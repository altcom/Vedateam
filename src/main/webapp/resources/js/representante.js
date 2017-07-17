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
	
	circleFaturamento.animate(faturamentoPorcentagem, 1);
});