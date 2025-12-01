<script lang="ts">
	import type { IEducationalInstitution } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.interface';
	import type { IGetUserProfileDetailsResponse } from '$lib/api/queries/users/get-user-profile-details/get-user-profile-details.interface';
	import FormInput from '$lib/components/atoms/FormInput.svelte';
	import SelectInput from '$lib/components/atoms/SelectInput.svelte';
	import { Validators } from '$lib/shared/helpers/validators';
	import type { IFormController } from '$lib/shared/interfaces/input-controller';
	import type { SelectInputOption } from '$lib/shared/interfaces/select-input-option';

	type FormField = keyof IGetUserProfileDetailsResponse;

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

	let selectedInstitution: IEducationalInstitution | null = $state(null);

	const formController: IFormController<FormField> = $state({
		publicId: {
			value: formValues.publicId,
			touched: !!formValues.publicId,
			error: null,
			required: false
		},
		cpf: {
			value: formValues.cpf,
			touched: !!formValues.cpf,
			error: null,
			required: false
		},
		birthDate: {
			value: formValues.birthDate,
			touched: !!formValues.birthDate,
			error: null,
			required: false
		},

		name: {
			value: formValues.name,
			touched: !!formValues.name,
			error: null,
			required: true
		},
		phone: {
			value: formValues.phone,
			touched: !!formValues.phone,
			error: null,
			required: true
		},
		email: {
			value: formValues.email,
			touched: !!formValues.email,
			error: null,
			required: true
		},
		institutionId: {
			value: formValues.institutionId,
			touched: !!formValues.institutionId,
			error: null,
			required: false
		},
		ra: {
			value: formValues.ra,
			touched: !!formValues.ra,
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

<div
	class="grid w-full max-w-230 grid-cols-1 place-items-center gap-0 sm:grid-cols-2 sm:gap-3 xl:gap-5"
>
	<FormInput
		label={'Nome Completo'}
		placeholder={'João dos Santos'}
		height="90px"
		bind:value={formController.name.value}
		error={formController.name.error}
		onInput={(value) => onInput('name', value)}
	/>
	<FormInput
		label={'Id Público'}
		disabled={true}
		height="90px"
		value={formController.publicId.value}
		onInput={(value) => onInput('publicId', value)}
	/>
	<FormInput
		label={'Telefone'}
		placeholder={'(43) 99999-9999'}
		mask="phone"
		height="90px"
		bind:value={formController.phone.value}
		error={formController.phone.error}
		onInput={(value) => onInput('phone', value)}
	/>
	<FormInput
		label={'E-mail'}
		placeholder={'joao@gmail.com'}
		type="email"
		height="90px"
		bind:value={formController.email.value}
		error={formController.email.error}
		onInput={(value) => onInput('email', value)}
	/>
	<FormInput
		label={'CPF'}
		disabled={true}
		mask="cpf"
		height="90px"
		bind:value={formController.cpf.value}
		error={formController.cpf.error}
		onInput={(value) => onInput('cpf', value)}
	/>
	<SelectInput
		label={'Instituição'}
		placeholder={'Selecione sua instituição'}
		height="90px"
		options={insituitionSelectInputOptions}
		onChange={(value) => onInput('institutionId', value)}
		bind:value={formController.institutionId.value}
		error={formController.institutionId.error}
	/>
	<FormInput
		label={'Data de Nascimento'}
		type="date"
		disabled={true}
		height="90px"
		bind:value={formController.birthDate.value}
		error={formController.birthDate.error}
		onInput={(value) => onInput('birthDate', value)}
	/>
	{#if selectedInstitution && selectedInstitution.isUtfpr}
		<FormInput
			label={'RA'}
			placeholder={'0000000'}
			mask="ra"
			height="90px"
			bind:value={formController.ra.value}
			error={formController.ra.error}
			onInput={(value) => onInput('ra', value)}
		/>
	{/if}
</div>
