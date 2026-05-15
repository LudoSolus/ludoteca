<script lang="ts">
	import type { IEducationalInstitution } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.interface';
	import FormInput from '$lib/components/atoms/FormInput.svelte';
	import SelectInput from '$lib/components/atoms/SelectInput.svelte';
	import { Validators } from '$lib/shared/helpers/validators';
	import { inputHasValue, type IFormController } from '$lib/shared/interfaces/input-controller';
	import type { SelectInputOption } from '$lib/shared/interfaces/select-input-option';
	import CheckboxInput from '$lib/components/atoms/CheckboxInput.svelte';

	type FormField =
		| 'name'
		| 'phone'
		| 'email'
		| 'cpf'
		| 'birthDate'
		| 'institutionId'
		| 'ra'
		| 'userRole';

	let {
		isValid = $bindable(),
		formValues = $bindable(),
		educationalInstitutions,
		type
	} = $props<{
		type: 'create' | 'edit';
		isValid: boolean;
		formValues: Record<string, string>;
		educationalInstitutions: IEducationalInstitution[];
	}>();

	$effect(() => {
		console.log(formValues);
		if (formValues) {
			Object.keys(formController).forEach((key: string) => {
				const formKey = key as keyof typeof formController;
				const val = formValues[formKey];
				if (inputHasValue(val)) {
					formController[formKey].touched = true;
				}
				formController[formKey].value = val;
			});
			isValid = validateForm();
		}
	});

	const isEditMode = $derived(type === 'edit');

	const validators = new Validators();

	const formController: IFormController<FormField> = $state({
		name: {
			value: formValues.name,
			touched: inputHasValue(formValues.name) || type === 'edit',
			error: null,
			required: true
		},
		phone: {
			value: formValues.phone,
			touched: inputHasValue(formValues.phone) || type === 'edit',
			error: null,
			required: true
		},
		email: {
			value: formValues.email,
			touched: inputHasValue(formValues.email) || type === 'edit',
			error: null,
			required: true
		},
		cpf: {
			value: formValues.cpf,
			touched: inputHasValue(formValues.cpf) || type === 'edit',
			error: null,
			required: true
		},
		birthDate: {
			value: formValues.birthDate,
			touched: inputHasValue(formValues.birthDate) || type === 'edit',
			error: null,
			required: true
		},
		institutionId: {
			value: formValues.institutionId,
			touched: inputHasValue(formValues.institutionId) || type === 'edit',
			error: null,
			required: true
		},
		ra: {
			value: formValues.ra,
			touched: inputHasValue(formValues.ra) || type === 'edit',
			error: null,
			required: false
		},
		userRole: {
			value: formValues.userRole,
			touched: false,
			error: null,
			required: true
		}
	});

	const instutionSelectInputOptions: SelectInputOption[] = $derived(
		educationalInstitutions.map((institution: IEducationalInstitution) => {
			return {
				label: institution.institutionName,
				value: institution.institutionId
			};
		})
	);

	function validateForm(): boolean {
		if (!isEditMode) {
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
		}

		let raIsValid: boolean = true;

		if (utfprInstitutionIsSelected()) {
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

	function onChangeUserRole(checked: boolean) {
		const isAdmin = checked;
		const newRoleValue = isAdmin ? 'ADMIN' : 'USER';
		formValues.userRole = newRoleValue;
	}

	function utfprInstitutionIsSelected(): boolean {
		return formValues.institutionId === '16610773-99dd-4df0-901f-041a3936d5d6';
	}
</script>

<div class="form-container flex w-full flex-wrap items-start gap-0 sm:gap-3 xl:gap-5">
	<FormInput
		label={'Nome Completo'}
		placeholder={'João dos Santos'}
		disabled={isEditMode}
		width="300px"
		height="90px"
		bind:value={formValues.name}
		error={formController.name.error}
	/>
	<FormInput
		label={'Telefone'}
		placeholder={'(43) 99999-9999'}
		disabled={isEditMode}
		mask="phone"
		width="300px"
		height="90px"
		bind:value={formValues.phone}
		error={formController.phone.error}
	/>
	<FormInput
		label={'E-mail'}
		placeholder={'joao@gmail.com'}
		disabled={isEditMode}
		type="email"
		width="300px"
		height="90px"
		bind:value={formValues.email}
		error={formController.email.error}
	/>

	{#if !isEditMode}
		<FormInput
			label={'CPF'}
			placeholder={'000.000.000-00'}
			disabled={isEditMode}
			mask="cpf"
			width="300px"
			height="90px"
			bind:value={formValues.cpf}
			error={formController.cpf.error}
		/>
		<FormInput
			label={'Data de Nascimento'}
			type="date"
			disabled={isEditMode}
			width="300px"
			height="90px"
			bind:value={formValues.birthDate}
			error={formController.birthDate.error}
		/>
		<SelectInput
			label={'Instituição'}
			placeholder={'Selecione sua instituição'}
			disabled={isEditMode}
			width="300px"
			height="90px"
			options={instutionSelectInputOptions}
			bind:value={formValues.institutionId}
			error={formController.institutionId.error}
		/>
	{/if}

	{#if utfprInstitutionIsSelected()}
		<FormInput
			label={'RA'}
			placeholder={'0000000'}
			mask="ra"
			disabled={isEditMode}
			width="300px"
			height="90px"
			bind:value={formValues.ra}
			error={formController.ra.error}
		/>
	{/if}

	<div class="flex w-full items-center">
		<CheckboxInput
			label="Administrador do Sistema?"
			checked={formValues.userRole === 'ADMIN'}
			onChange={(checked) => onChangeUserRole(checked)}
		/>
	</div>
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
