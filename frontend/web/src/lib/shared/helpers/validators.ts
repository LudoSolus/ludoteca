export class Validators {
    public completeName(value: string): string | null {
        if (!value) {
            return "Nome completo é obrigatório.";
        }
        const nameParts = value.split(' ').filter(part => part.length > 0);
        if (nameParts.length < 2) {
            return "Coloque seu sobrenome.";
        }
        return null;
    }

    public email(value: string): string | null {
        if (!value) {
            return "E-mail é obrigatório.";
        }
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(value)) {
            return "E-mail inválido.";
        }
        return null;
    }

    public phoneNumber(value: string): string | null {
        if (!value) {
            return "Número de telefone é obrigatório.";
        }
        const phoneNumberRegex = /^\(\d{2}\)\s?\d{5}-\d{4}$/;
        if (!phoneNumberRegex.test(value)) {
            return "Número de telefone inválido.";
        }
        return null;
    }

    public cpf(value: string): string | null {
        if (!value) {
            return "CPF é obrigatório.";
        }

        const cpf = value.replaceAll(/[^\d]/g, '');

        if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) {
            return "CPF inválido.";
        }

        let sum = 0;
        let remainder: number;

        for (let i = 1; i <= 9; i++) {
            sum = sum + Number.parseInt(cpf.substring(i - 1, i)) * (11 - i);
        }

        remainder = (sum * 10) % 11;

        if ((remainder === 10) || (remainder === 11)) {
            remainder = 0;
        }

        if (remainder !== Number.parseInt(cpf.substring(9, 10))) {
            return "CPF inválido.";
        }

        sum = 0;
        for (let i = 1; i <= 10; i++) {
            sum = sum + Number.parseInt(cpf.substring(i - 1, i)) * (12 - i);
        }
        remainder = (sum * 10) % 11;

        if ((remainder === 10) || (remainder === 11)) {
            remainder = 0;
        }

        if (remainder !== Number.parseInt(cpf.substring(10, 11))) {
            return "CPF inválido.";
        }

        return null
    }

    public password(value: string): string | null {
        if (!value) {
            return "Senha é obrigatória.";
        }
        if (value.length < 6) {
            return "A senha deve conter pelo menos 6 caracteres."
        }
        return null
    }

    public ra(value: string): string | null {
        if (!value) {
            return "RA é obrigatório.";
        }

        if (value.length !== 7){
            return "RA inválido."
        }
        
        return null
    }
}