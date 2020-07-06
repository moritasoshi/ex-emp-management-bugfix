$(function() {
	//標準エラーメッセージの変更
	$.extend($.validator.messages, {
		required: '*入力必須です',
		email: '*正しいメールアドレスの形式で入力して下さい',
		zipCode: '*正しい郵便番号の形式（数字7桁）で入力してください',
		phone: '*正しい電話番号の形式（000-0000-0000）で入力してください',
		onlyNum: '*正しい数値を入力してください'
	});

	//追加ルールの定義
	var methods = {
		zipCode: function(value, element) {
			return this.optional(element) || /^\d{7}$/.test(value);
		},
		phone: function(value, element) {
			return this.optional(element) || /^\d{3}-\d{4}-\d{4}$/.test(value);
		},
		onlyNum: function(value, element) {
			return this.optional(element) || /^\d+?/.test(value);
		}
	};

	//メソッドの追加
	$.each(methods, function(key) {
		$.validator.addMethod(key, this);
	});

	//入力項目の検証ルール定義
	var rules = {
		name: { required: true },
		gender: { required: true },
		mailAddress: { required: true, email: true },
		zipCode: { required: true, zipCode: true },
		address: { required: true },
		telephone: { required: true, phone: true },
		salary: { required: true, onlyNum: true },
		dependentsCount: { required: true, onlyNum: true }
	};

	//入力項目ごとのエラーメッセージ定義
	var messages = {
		gender: {
			required: '*性別を選択してください'
		},
		salary: {
			onlyNum: '*給料を入力してください'
		},
		dependentsCount: {
			onlyNum: '*人数を入力してください'
		}
	};

	$(function() {
		$('#register-form').validate({
			rules: rules,
			messages: messages,

			//エラーメッセージ出力箇所調整
			errorPlacement: function(error, element) {
				error.addClass('error-messages'); // エラーメッセージ用のクラスを追加
				error.appendTo(element.parent());
			}
		});
	});
});
