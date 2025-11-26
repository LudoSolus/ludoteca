<script lang="ts">
	import FormInput from '$lib/components/atoms/FormInput.svelte';
	import { Validators } from '$lib/shared/helpers/validators';
	import type { IFormController } from '$lib/shared/interfaces/input-controller';

	type FormField = 'email' | 'password';

	let { isValid = $bindable(), formValues = $bindable() } = $props<{
		isValid: boolean;
		formValues: Record<string, string>;
	}>();

	const validators = new Validators();

	const formController: IFormController<FormField> = $state({
		email: {
			value: formValues.email,
			touched: false,
			error: null,
			required: true
		},
		password: {
			value: formValues.password,
			touched: false,
			error: null,
			required: true
		}
	});

	function validateForm(): boolean {
		if (formController.email.touched) {
			formController.email.error = validators.email(formController.email.value);
		}
		if (formController.password.touched) {
			formController.password.error = validators.password(formController.password.value);
		}

		return Object.values(formController).every(
			(field) => (field.touched || !field.required) && !field.error
		);
	}

	function onInput(formName: FormField, value: string) {
		formController[formName].touched = true;
		formValues[formName] = formController[formName].value;
		isValid = validateForm();
	}
</script>

<div class="form-container flex w-full flex-wrap items-center gap-0 sm:gap-3 xl:gap-5">
	<FormInput
		label={'E-mail'}
		placeholder={'joao@gmail.com'}
		type="email"
		width="450px"
		height="90px"
		bind:value={formController.email.value}
		error={formController.email.error}
		onInput={(value) => onInput('email', value)}
	/>
	<FormInput
		label={'Senha'}
		type="password"
		width="450px"
		height="90px"
		bind:value={formController.password.value}
		error={formController.password.error}
		onInput={(value) => onInput('password', value)}
	/>
</div>

<style>
	.form-container {
		max-width: 300px;
		justify-content: center;
	}

	@media (min-width: 330px) {
		.form-container {
			max-width: 320px;
		}
	}

	@media (min-width: 725px) {
		.form-container {
			min-width: 620px;
			max-width: 620px;
			justify-content: flex-start;
		}
	}
</style>
