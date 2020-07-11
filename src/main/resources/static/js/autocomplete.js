$(function() {
	$('#name').keyup(function() {
		$.ajax({
			url: '/ajax/find',
			data: { name: $('#name').val() },
			dataType: 'json'
		})
			.done(function(nameList) {
				// 成功したときの処理
				// オートコンプリート値を設定する
				$('#nameList').empty();
				for (var name of nameList) {
					let op = document.createElement('option');
					op.value = name;
					document.getElementById('nameList').appendChild(op);
				}
			})
			.fail(function() {
				alart('リクエスト時にエラーが発生しました');
			});
	});
});
