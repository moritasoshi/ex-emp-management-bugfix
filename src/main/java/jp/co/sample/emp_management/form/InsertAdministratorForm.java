package jp.co.sample.emp_management.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 管理者情報登録時に使用するフォーム.
 * 
 * @author igamasayuki
 * 
 */
public class InsertAdministratorForm {
	/** 名前 */
	@NotBlank(message = "名前を入力してください")
	private String name;

	/** メールアドレス */
	@NotBlank(message = "正しいメールアドレスを入力してください")
	@Email(message = "正しいメールアドレスを入力してください")
	private String mailAddress;

	/** パスワード */
	@Pattern(regexp = "^[a-z\\d]{8,100}$", message = "半角英数字8桁以上100桁以内で入力してください")
	private String password;

	private String validationPassword;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
				+ "]";
	}

	public String getValidationPassword() {
		return validationPassword;
	}

	public void setValidationPassword(String validationPassword) {
		this.validationPassword = validationPassword;
	}

}
