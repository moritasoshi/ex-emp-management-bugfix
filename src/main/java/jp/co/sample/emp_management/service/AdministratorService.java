package jp.co.sample.emp_management.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sample.emp_management.domain.Administrator;
import jp.co.sample.emp_management.repository.AdministratorRepository;

/**
 * 管理者情報を操作するサービス.
 * 
 * @author igamasayuki
 *
 */
@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository administratorRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * 管理者情報を登録します.
	 * 
	 * @param administrator 管理者情報
	 */
	public boolean insert(Administrator administrator) {
		// メールアドレスをDBと照合
		Administrator checkedAdmin = administratorRepository.findByMailAddress(administrator.getMailAddress());

		// パスワードをハッシュ化
		String password = administrator.getPassword();
		administrator.setPassword(passwordEncoder.encode(password));

		// ハッシュ化確認用
		System.out.println("前 : " + password);
		System.out.println("後 : " + administrator.getPassword());

		// DBに同じメールアドレスが存在しない場合のみinsert文を実行
		if (Objects.isNull(checkedAdmin)) {
			administratorRepository.insert(administrator);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * ログインをします.
	 * 
	 * @param mailAddress メールアドレス
	 * @param password    パスワード
	 * @return 管理者情報 存在しない場合はnullが返ります
	 */
	public Administrator login(String mailAddress, String password) {
		// メールアドレスで管理者を取得
		Administrator administrator = administratorRepository.findByMailAddress(mailAddress);
		System.out.println("ここです");
		// ハッシュ化されたDBのパスワードと入力値が一致するか確認
		if (passwordEncoder.matches(password, administrator.getPassword())) {
			System.out.println("一致したよ");
			administrator.setPassword(password); // 入力されたパスワードに変更して管理者を返す
			return administrator;
		} else {
			return null;
		}
	}

}
