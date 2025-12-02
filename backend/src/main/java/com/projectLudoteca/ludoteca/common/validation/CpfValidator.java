package com.projectLudoteca.ludoteca.common.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<Cpf, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) {
            return true; // usa @NotBlank separado se for obrigatório
        }

        // Remove caracteres especiais (pontos e traços)
        String cpf = value.replaceAll("\\D", "");

        // Deve conter exatamente 11 dígitos
        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (ex: 111.111.111-11)
        if (cpf.chars().distinct().count() == 1) {
            return false;
        }

        try {
            return isValidCPF(cpf);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidCPF(String cpf) {
        int[] peso = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        int soma1 = 0, soma2 = 0;
        for (int i = 0; i < 9; i++) {
            int num = cpf.charAt(i) - '0';
            soma1 += num * peso[i + 1];
            soma2 += num * peso[i];
        }

        int digito1 = 11 - (soma1 % 11);
        digito1 = digito1 > 9 ? 0 : digito1;

        soma2 += digito1 * peso[9];
        int digito2 = 11 - (soma2 % 11);
        digito2 = digito2 > 9 ? 0 : digito2;

        return cpf.endsWith("" + digito1 + digito2);
    }
}
