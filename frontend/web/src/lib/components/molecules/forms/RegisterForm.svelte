<script lang="ts">
	import type { IEducationalInstitution } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.interface';
	import FormInput from '$lib/components/atoms/FormInput.svelte';
	import SelectInput from '$lib/components/atoms/SelectInput.svelte';
	import { Validators } from '$lib/shared/helpers/validators';
	import { inputHasValue, type IFormController } from '$lib/shared/interfaces/input-controller';
	import type { SelectInputOption } from '$lib/shared/interfaces/select-input-option';
	import { untrack } from 'svelte';

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

	let selectedInstitution: IEducationalInstitution | null = $derived(
		educationalInstitutions.find(
			(ei: IEducationalInstitution) => ei.institutionId == formValues.institutionId
		)
	);

	function validateForm(): boolean {
		if (formController.name.touched) {
			formController.name.error = validators.completeName(formValues.name);
		}
		if (formController.phone.touched) {
			formController.phone.error = validators.phoneNumber(formValues.phone);
		}
		if (formController.email.touched) {
			formController.email.error = validators.email(formValues.email);
		}
		if (formController.cpf.touched) {
			formController.cpf.error = validators.cpf(formValues.cpf);
		}
		if (formController.password.touched) {
			formController.password.error = validators.password(formValues.password);
		}

		let raIsValid: boolean = true;

		if (selectedInstitution && selectedInstitution.isUtfpr) {
			raIsValid = false;
			if (formController.ra.touched) {
				formController.ra.error = validators.ra(formValues.ra);
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
</script>

<div class="form-container flex w-full flex-wrap items-start gap-0 sm:gap-3 xl:gap-5">
	<FormInput
		label={'Nome Completo'}
		placeholder={'João dos Santos'}
		width="300px"
		height="90px"
		bind:value={formValues.name}
		error={formController.name.error}
	/>
	<FormInput
		label={'Telefone'}
		placeholder={'(43) 99999-9999'}
		mask="phone"
		width="300px"
		height="90px"
		bind:value={formValues.phone}
		error={formController.phone.error}
	/>
	<FormInput
		label={'E-mail'}
		placeholder={'joao@gmail.com'}
		type="email"
		width="300px"
		height="90px"
		bind:value={formValues.email}
		error={formController.email.error}
	/>
	<FormInput
		label={'CPF'}
		placeholder={'000.000.000-00'}
		mask="cpf"
		width="300px"
		height="90px"
		bind:value={formValues.cpf}
		error={formController.cpf.error}
	/>
	<FormInput
		label={'Data de Nascimento'}
		type="date"
		width="300px"
		height="90px"
		bind:value={formValues.birthDate}
		error={formController.birthDate.error}
	/>
	<SelectInput
		label={'Instituição'}
		placeholder={'Selecione sua instituição'}
		width="300px"
		height="90px"
		options={insituitionSelectInputOptions}
		bind:value={formValues.institutionId}
		error={formController.institutionId.error}
	/>
	<FormInput
		label={'Senha'}
		type="password"
		width="300px"
		height="90px"
		bind:value={formValues.password}
		error={formController.password.error}
	/>
	{#if selectedInstitution && selectedInstitution.isUtfpr}
		<FormInput
			label={'RA'}
			placeholder={'0000000'}
			mask="ra"
			width="300px"
			height="90px"
			bind:value={formValues.ra}
			error={formController.ra.error}
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
