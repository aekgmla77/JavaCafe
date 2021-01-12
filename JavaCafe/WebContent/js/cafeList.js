/**
 * cafeList.js
 */
$(function() {
	$.ajax({
		url: '../getProList',
		dataType: 'json',
		success: showContents,
		error: showError
	});
})

function showError(result) {
	console.log(result)
}

function showContents(result) {
	console.log(result);
	let data = result;
	for (val of data) {
		//let db_value;
		let elem_1, elem_2, elem_3;
		// 첫번째 자식 요소.
		elem_1 = $('<a />').attr('href', val.link);
		let e1_img = $('<img />').attr('src',
			"../images/" + val.image).attr('alt',
				val.alt);
		e1_img.addClass("card-img-top");
		elem_1.append(e1_img);
		// 두번째 자식 요소
		elem_2 = $('<div />').addClass("card-body");
		let h4 = $('<h4 />').addClass("card-title");
		let a = $('<a />').attr('href', val.link).html(
			val.item);
		h4.append(a);
		let krw_price = new Intl.NumberFormat('ko-KR', {
			style: 'currency', currency: 'KRW' }).format(val.price);
		let h5 = $('<h5 />').html(krw_price);
		let p = $('<p />').addClass("card-text").html(
			val.content);
		elem_2.append(h4, h5, p);
		// 세번째 자식 요소
		elem_3 = $('<div />').addClass("card-footer");
		let star = '';
		for(let i = 0; i < val.like_it; i++){
			star += '&#9733;'
		}
		let small = $('<small />')
			.addClass("text-muted")
			.html(star);
		elem_3.append(small);

		let div_1, div_2;
		div_1 = $('<div />').addClass(
			"col-lg-4 col-md-6 mb-4");
		div_2 = $('<div />').addClass("card h-100");

		div_1.append(div_2);
		div_2.append(elem_1, elem_2, elem_3)

		$('.col-lg-9 .row').append(div_1);
	}

}