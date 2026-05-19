<script lang="ts">
	import FormInput from '$lib/components/atoms/FormInput.svelte';
	import { Validators } from '$lib/shared/helpers/validators';
	import { inputHasValue, type IFormController } from '$lib/shared/interfaces/input-controller';
	import { untrack } from 'svelte';

	type FormField = 'email' | 'password';

	let { isValid = $bindable(), formValues = $bindable() } = $props<{
		isValid: boolean;
		formValues: Record<string, string>;
	}>();

	$effect(() => {
		const snapshot = formValues ? { ...formValues } : null;

		untrack(() => {
			if (!snapshot) return;
			Object.keys(formController).forEach((key: string) => {
				const formKey = key as keyof typeof formController;
				const val = snapshot[formKey];
				if (inputHasValue(val)) {
					formController[formKey].touched = true;
				}
				formController[formKey].value = val;
			});
			isValid = validateForm();
		});
	});

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
			formController.email.error = validators.email(formValues.email);
		}
		if (formController.password.touched) {
			formController.password.error = validators.password(formValues.password);
		}

		return Object.values(formController).every(
			(field) => (field.touched || !field.required) && !field.error
		);
	}
</script>

<div class="form-container flex w-full flex-wrap items-center gap-0 sm:gap-3 xl:gap-5">
	<FormInput
		label={'E-mail'}
		placeholder={'joao@gmail.com'}
		type="email"
		width="450px"
		height="90px"
		bind:value={formValues.email}
		error={formController.email.error}
	/>
	<FormInput
		label={'Senha'}
		type="password"
		width="450px"
		height="90px"
		bind:value={formValues.password}
		error={formController.password.error}
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
