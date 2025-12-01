<script lang="ts">
	import type { IEducationalInstitution } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.interface';
	import FormInput from '$lib/components/atoms/FormInput.svelte';
	import SelectInput from '$lib/components/atoms/SelectInput.svelte';
	import { Validators } from '$lib/shared/helpers/validators';
	import type { IFormController } from '$lib/shared/interfaces/input-controller';
	import type { SelectInputOption } from '$lib/shared/interfaces/select-input-option';

	type FormField =
		| 'name'
		| 'phone'
		| 'email'
		| 'cpf'
		| 'birthDate'
		| 'institutionId'
		| 'password'
		| 'ra';

	let {
		isValid = $bindable(),
		formValues = $bindable(),
		educationalInstitutions
	} = $props<{
		isValid: boolean;
		formValues: Record<string, string>;
		educationalInstitutions: IEducationalInstitution[];
	}>();

	const validators = new Validators();

	const formController: IFormController<FormField> = $state({
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
		institutionId: {
			value: formValues.institutionId,
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
	});

	const insituitionSelectInputOptions: SelectInputOption[] = $derived(
		educationalInstitutions.map((institution: IEducationalInstitution) => {
			return {
				label: institution.institutionName,
				value: institution.institutionId
			};
		})
	);

	let selectedInstitution: IEducationalInstitution | null = $state(null);

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

		let raIsValid: boolean = true;

		if (selectedInstitution && selectedInstitution.isUtfpr) {
			raIsValid = false;
			if (formController.ra.touched) {
				formController.ra.error = validators.ra(formController.ra.value);
				raIsValid = formController.ra.error === null;
			}
		} else {
			formController.ra.touched = false;
			formController.ra.error = null;
		}

		return (
			raIsValid &&
			Object.values(formController).every(
				(field) => (field.touched || !field.required) && !field.error
			)
		);
	}

	function updateSelectedInstitution() {
		selectedInstitution = educationalInstitutions.find(
			(ei: IEducationalInstitution) => ei.institutionId == formController.institutionId.value
		);
	}

	function onInput(formName: FormField, value: string) {
		formController[formName].touched = true;
		if (formName == 'institutionId') updateSelectedInstitution();
		formValues[formName] = formController[formName].value;
		isValid = validateForm();
	}
</script>

<div class="form-container flex w-full flex-wrap items-start gap-0 sm:gap-3 xl:gap-5">
	<FormInput
		label={'Nome Completo'}
		placeholder={'João dos Santos'}
		width="300px"
		height="90px"
		bind:value={formController.name.value}
		error={formController.name.error}
		onInput={(value) => onInput('name', value)}
	/>
	<FormInput
		label={'Telefone'}
		placeholder={'(43) 99999-9999'}
		mask="phone"
		width="300px"
		height="90px"
		bind:value={formController.phone.value}
		error={formController.phone.error}
		onInput={(value) => onInput('phone', value)}
	/>
	<FormInput
		label={'E-mail'}
		placeholder={'joao@gmail.com'}
		type="email"
		width="300px"
		height="90px"
		bind:value={formController.email.value}
		error={formController.email.error}
		onInput={(value) => onInput('email', value)}
	/>
	<FormInput
		label={'CPF'}
		placeholder={'000.000.000-00'}
		mask="cpf"
		width="300px"
		height="90px"
		bind:value={formController.cpf.value}
		error={formController.cpf.error}
		onInput={(value) => onInput('cpf', value)}
	/>
	<FormInput
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
		options={insituitionSelectInputOptions}
		onChange={(value) => onInput('institutionId', value)}
		bind:value={formController.institutionId.value}
		error={formController.institutionId.error}
	/>
	<FormInput
		label={'Senha'}
		type="password"
		width="300px"
		height="90px"
		bind:value={formController.password.value}
		error={formController.password.error}
		onInput={(value) => onInput('password', value)}
	/>
	{#if selectedInstitution && selectedInstitution.isUtfpr}
		<FormInput
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
		max-width: 320px;
		justify-content: center;
	}

	@media (min-width: 725px) {
		.form-container {
			min-width: 620px;
			max-width: 620px;
			justify-content: flex-start;
		}
	}
</style>
