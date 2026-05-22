<script lang="ts">
	import { Validators } from '$lib/shared/helpers/validators';
	import type { IFormController } from '$lib/shared/interfaces/input-controller';
	import { toast } from 'svoast';
	import Button from '../atoms/Button.svelte';
	import FormInput from '../atoms/FormInput.svelte';

	type FormField = 'password' | 'confirmPassword';

	let { onChangePassword, isLoading } = $props<{
		onChangePassword: (newPassword: string) => void;
		isLoading: boolean;
	}>();

	const validators = new Validators();
	let isValid: boolean = false;

	const formController: IFormController<FormField> = $state({
		password: {
			value: '',
			touched: false,
			error: null,
			required: true
		},
		confirmPassword: {
			value: '',
			touched: false,
			error: null,
			required: true
		}
	});

	function handleChangePassword() {
		if (!isValid) {
			toast.error('Preencha os campos corretamente.');
			return;
		}

		onChangePassword(formController.password.value);
	}

	function validateForm(): boolean {
		if (formController.password.touched) {
			formController.password.error = validators.password(formController.password.value);
		}

		if (formController.password.touched && formController.confirmPassword.touched) {
			if (formController.password.value != formController.confirmPassword.value)
				formController.confirmPassword.error = 'As duas senhas devem ser iguais.';
			else formController.confirmPassword.error = null;
		}

		return Object.values(formController).every(
			(field) => (field.touched || !field.required) && !field.error
		);
	}

	function onInput(formName: FormField, value: string) {
		formController[formName].touched = true;
		isValid = validateForm();
	}
</script>

<main class="flex w-full flex-col items-center gap-6 sm:gap-10">
	<h1 class="text-center text-3xl">Criar Nova Senha</h1>
	<p>Insira sua nova senha, se lembre que ela deve ter 8 caracteres incluindo letras e números.</p>
	<div class="flex w-full flex-col gap-2">
		<FormInput
			label="Nova Senha"
			type="password"
			height="90px"
			bind:value={formController.password.value}
			error={formController.password.error}
			onInput={(v) => onInput('password', v)}
		/>
		<FormInput
			label="Confirme a Nova Senha"
			type="password"
			height="90px"
			bind:value={formController.confirmPassword.value}
			error={formController.confirmPassword.error}
			onInput={(v) => onInput('confirmPassword', v)}
		/>
		<a class="redirect-text text-[12px] w-fit" href="/auth/login">Voltar para o login</a>
	</div>
	<div class="w-full sm:max-w-50">
		<Button
			text="Enviar"
			width="100%"
			height="35px"
			onClick={handleChangePassword}
			loading={isLoading}
		/>
	</div>
</main>
