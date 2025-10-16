<script lang="ts">
	import Input from '$lib/components/atoms/Input.svelte';
	import SelectInput from '$lib/components/atoms/SelectInput.svelte';
	import { Validators } from '$lib/helpers/validators';
	import type { InputController } from '$lib/interfaces/input-controller';
	import type { SelectInputOption } from '$lib/interfaces/select-input-option';

	type FormField =
		| 'name'
		| 'phone'
		| 'email'
		| 'cpf'
		| 'birthDate'
		| 'institute'
		| 'password'
		| 'ra';

	export let isValid: boolean = false;
	export let formValues: Record<string, string>;

	const validators = new Validators();

	const instituteOptions: SelectInputOption[] = [
		{
			label: 'Universidade Tecnológica Federal do Paraná (UTFPR)',
			value: 'UTFPR'
		},
		{
			label: 'Universidade Estadual de Londrina (UEL)',
			value: 'UEL'
		}
	];

	const formController: Record<FormField, InputController> = {
		name: {
			value: formValues.name,
			touched: false,
			error: null,
			required: true
		},
		phone: {
			value: formValues.phone,
			touched: false,
			error: null,
			required: true
		},
		email: {
			value: formValues.email,
			touched: false,
			error: null,
			required: true
		},
		cpf: {
			value: formValues.cpf,
			touched: false,
			error: null,
			required: true
		},
		birthDate: {
			value: formValues.birthDate,
			touched: false,
			error: null,
			required: true
		},
		institute: {
			value: formValues.institute,
			touched: false,
			error: null,
			required: true
		},
		password: {
			value: formValues.password,
			touched: false,
			error: null,
			required: true
		},
		ra: {
			value: formValues.ra,
			touched: false,
			error: null,
			required: false
		}
	};

	function validateForm(): boolean {
		if (formController.name.touched) {
			formController.name.error = validators.completeName(formController.name.value);
		}
		if (formController.phone.touched) {
			formController.phone.error = validators.phoneNumber(formController.phone.value);
		}
		if (formController.email.touched) {
			formController.email.error = validators.email(formController.email.value);
		}
		if (formController.cpf.touched) {
			formController.cpf.error = validators.cpf(formController.cpf.value);
		}
		if (formController.password.touched) {
			formController.password.error = validators.password(formController.password.value);
		}

		const utfprStudent = formController.institute.value === 'UTFPR';
		let raIsValid: boolean = true;

		if (utfprStudent) {
			raIsValid = false;
			if (formController.ra.touched) {
				formController.ra.error = validators.ra(formController.ra.value);
				raIsValid = formController.ra.error === null;
			}
		} else{
			formController.ra.touched = false
			formController.ra.error = null
		}

		return (
			raIsValid &&
			Object.values(formController).every(
				(field) => (field.touched || !field.required) && !field.error
			)
		);
	}

	function onInput(formName: FormField, value: string) {
		formController[formName].touched = true;
		formValues[formName] = formController[formName].value;
		isValid = validateForm();
	}
</script>

<div class="form-container flex w-full flex-wrap items-start justify-start gap-5">
	<Input
		label={'Nome Completo'}
		placeholder={'João dos Santos'}
		width="300px"
		height="90px"
		bind:value={formController.name.value}
		error={formController.name.error}
		onInput={(value) => onInput('name', value)}
	/>
	<Input
		label={'Telefone'}
		placeholder={'(43) 99999-9999'}
		mask="phone"
		width="300px"
		height="90px"
		bind:value={formController.phone.value}
		error={formController.phone.error}
		onInput={(value) => onInput('phone', value)}
	/>
	<Input
		label={'E-mail'}
		placeholder={'joao@gmail.com'}
		type="email"
		width="300px"
		height="90px"
		bind:value={formController.email.value}
		error={formController.email.error}
		onInput={(value) => onInput('email', value)}
	/>
	<Input
		label={'CPF'}
		placeholder={'000.000.000-00'}
		mask="cpf"
		width="300px"
		height="90px"
		bind:value={formController.cpf.value}
		error={formController.cpf.error}
		onInput={(value) => onInput('cpf', value)}
	/>
	<Input
		label={'Data de Nascimento'}
		type="date"
		width="300px"
		height="90px"
		bind:value={formController.birthDate.value}
		error={formController.birthDate.error}
		onInput={(value) => onInput('birthDate', value)}
	/>
	<SelectInput
		label={'Instituição'}
		placeholder={'Selecione sua instituição'}
		width="300px"
		height="90px"
		options={instituteOptions}
		onChange={(value) => onInput('institute', value)}
		bind:value={formController.institute.value}
		error={formController.institute.error}
	/>
	<Input
		label={'Senha'}
		type="password"
		width="300px"
		height="90px"
		bind:value={formController.password.value}
		error={formController.password.error}
		onInput={(value) => onInput('password', value)}
	/>
	{#if formController.institute.value == 'UTFPR'}
		<Input
			label={'RA'}
			placeholder={'0000000'}
			mask="ra"
			width="300px"
			height="90px"
			bind:value={formController.ra.value}
			error={formController.ra.error}
			onInput={(value) => onInput('ra', value)}
		/>
	{/if}
</div>

<style>
	.form-container {
		min-width: 620px;
		max-width: 620px;
	}
</style>
