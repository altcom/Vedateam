package br.net.altcom.calculadora;

public class TabelaDePontos {

	public static int pontosHabilitadorRepresentante(double porcetagemDoHabilitador) {
		if (porcetagemDoHabilitador <= 89)
			return 0;

		if (porcetagemDoHabilitador <= 94)
			return 75;

		if (porcetagemDoHabilitador <= 99)
			return 90;

		if (porcetagemDoHabilitador <= 109)
			return 105;

		if (porcetagemDoHabilitador <= 119)
			return 120;

		if (porcetagemDoHabilitador <= 129)
			return 135;

		if (porcetagemDoHabilitador <= 139)
			return 150;

		if (porcetagemDoHabilitador <= 149)
			return 180;

		if (porcetagemDoHabilitador <= 169)
			return 210;

		if (porcetagemDoHabilitador <= 189)
			return 240;

		if (porcetagemDoHabilitador <= 199)
			return 270;

		if (porcetagemDoHabilitador >= 200)
			return 300;

		return -5;
	}
}
