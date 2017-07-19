$(document).ready(function(){
	var faturamentoPorcentagem = $("#faturamentoBar").val();
	var circleFaturamento = new ProgressBar.Circle('#faturamento', {
        color: '#e74c3c',
        duration: 3000,
        strokeWidth: 10,
        trailColor: '#f7f7f7',
        easing: 'easeInOut',
        text: {
        	value: '<p>' + parseInt(faturamentoPorcentagem * 100) + '%</p>'
        }
    });
	circleFaturamento.animate(Math.min(faturamentoPorcentagem, 1), 1);
	
	var btnClienteNovos = $("#btn-clientesNovos");
	var btnClientesAtivos = $("#btn-clientesAtivos");
	var btnClientesNaBase = $("#btn-clientesNaBase");
	
	var btnCN = false;
	var btnCA = false;
	var btnCB = false;
	
	btnClienteNovos.click(function() {
		var icone = $("#btn-clientesNovos .glyphicon"); 
		if(!btnCN){
			icone.removeClass("glyphicon-triangle-bottom");
			icone.addClass("glyphicon-triangle-top");
			$("#clientesNovos").css("display", "block");
		}else{
			icone.removeClass("glyphicon-triangle-top");
			icone.addClass("glyphicon-triangle-bottom");
			$("#clientesNovos").css("display", "none");
		}
		btnCN = !btnCN;
	});
	
	btnClientesAtivos.click(function() {
		var icone = $("#btn-clientesAtivos .glyphicon"); 
		if(!btnCA){
			icone.removeClass("glyphicon-triangle-bottom");
			icone.addClass("glyphicon-triangle-top");
			$("#clientesAtivos").css("display", "block");
		}else{
			icone.removeClass("glyphicon-triangle-top");
			icone.addClass("glyphicon-triangle-bottom");
			$("#clientesAtivos").css("display", "none");
		}
		btnCA = !btnCA;
	});
	
	btnClientesNaBase.click(function() {
		var icone = $("#btn-clientesNaBase .glyphicon"); 
		if(!btnCB){
			icone.removeClass("glyphicon-triangle-bottom");
			icone.addClass("glyphicon-triangle-top");
			$("#clientesNaBase").css("display", "block");
		}else{
			icone.removeClass("glyphicon-triangle-top");
			icone.addClass("glyphicon-triangle-bottom");
			$("#clientesNaBase").css("display", "none");
		}
		btnCB = !btnCB;
	});
});