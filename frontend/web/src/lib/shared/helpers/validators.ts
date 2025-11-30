type validatorReturn = string | null;

export class Validators {
	public completeName(value: string): validatorReturn {
		if (!value) {
			return 'Nome completo é obrigatório.';
		}
		const nameParts = value.split(' ').filter((part) => part.length > 0);
		if (nameParts.length < 2) {
			return 'Coloque seu sobrenome.';
		}
		return null;
	}

	public email(value: string): validatorReturn {
		if (!value) {
			return 'E-mail é obrigatório.';
		}
		const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		if (!emailRegex.test(value)) {
			return 'E-mail inválido.';
		}
		return null;
	}

	public phoneNumber(value: string): validatorReturn {
		if (!value) {
			return 'Número de telefone é obrigatório.';
		}
		const phoneNumberRegex = /^\(\d{2}\)\s?\d{5}-\d{4}$/;
		if (!phoneNumberRegex.test(value)) {
			return 'Número de telefone inválido.';
		}
		return null;
	}

	public cpf(value: string): validatorReturn {
		if (!value) {
			return 'CPF é obrigatório.';
		}

		const cpf = value.replaceAll(/[^\d]/g, '');

		if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) {
			return 'CPF inválido.';
		}

		let sum = 0;
		let remainder: number;

		for (let i = 1; i <= 9; i++) {
			sum = sum + Number.parseInt(cpf.substring(i - 1, i)) * (11 - i);
		}

		remainder = (sum * 10) % 11;

		if (remainder === 10 || remainder === 11) {
			remainder = 0;
		}

		if (remainder !== Number.parseInt(cpf.substring(9, 10))) {
			return 'CPF inválido.';
		}

		sum = 0;
		for (let i = 1; i <= 10; i++) {
			sum = sum + Number.parseInt(cpf.substring(i - 1, i)) * (12 - i);
		}
		remainder = (sum * 10) % 11;

		if (remainder === 10 || remainder === 11) {
			remainder = 0;
		}

		if (remainder !== Number.parseInt(cpf.substring(10, 11))) {
			return 'CPF inválido.';
		}

		return null;
	}

	public password(value: string): validatorReturn {
		if (!value) {
			return 'Senha é obrigatória.';
		}
		if (value.length < 8) {
			return 'Senha deve conter pelo menos 8 caracteres.';
		}

		const hasLetter = /[a-zA-Z]/.test(value);
		const hasNumber = /[0-9]/.test(value);
		if (!hasLetter || !hasNumber) {
			return 'A senha deve conter letras e números.';
		}

		return null;
	}

	public ra(value: string): validatorReturn {
		if (!value) {
			return 'RA é obrigatório.';
		}

		if (value.length !== 7) {
			return 'RA inválido.';
		}

		return null;
	}

	public isFilled(value: string): validatorReturn {
		if (!value || value.length < 1) {
			return 'Campo obrigatório.';
		}

		return null;
	}

	public url(value: string): validatorReturn {
		if (!value) {
			return 'Campo obrigatório.';
		}

		try {
			new URL(value);
			return null;
		} catch {
			return 'O campo deve ser um link.';
		}
	}

	public maxLenght(value: string, max: number): validatorReturn {
		if (!value) {
			return 'Campo obrigatório.';
		}

		if (value.length > max) {
			return `o campo deve ter no máximo ${max} caracteres.`;
		}

		return null;
	}
}
