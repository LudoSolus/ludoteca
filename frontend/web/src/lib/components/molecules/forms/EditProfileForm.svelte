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

	const formController: IFormController<FormField> = $state({
		publicId: {
			value: formValues.publicId,
			touched: false,
			error: null,
			required: false
		},
		cpf: {
			value: formValues.cpf,
			touched: false,
			error: null,
			required: false
		},
		birthDate: {
			value: formValues.birthDate,
			touched: false,
			error: null,
			required: false
		},

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
		instituitionId: {
			value: formValues.instituitionId,
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

		if (utfprInstituitionIsSelected()) {
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

	function onInput(formName: FormField, value: string) {
		formController[formName].touched = true;
		formValues[formName] = formController[formName].value;
		isValid = validateForm();
	}

	function utfprInstituitionIsSelected(): boolean {
		return formController.instituitionId.value === '16610773-99dd-4df0-901f-041a3936d5d6';
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
		onChange={(value) => onInput('instituitionId', value)}
		bind:value={formController.instituitionId.value}
		error={formController.instituitionId.error}
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
	{#if utfprInstituitionIsSelected()}
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
