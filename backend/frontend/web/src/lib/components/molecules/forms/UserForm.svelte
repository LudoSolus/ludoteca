<script lang="ts">
	import type { IEducationalInstitution } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.interface';
	import FormInput from '$lib/components/atoms/FormInput.svelte';
	import SelectInput from '$lib/components/atoms/SelectInput.svelte';
	import { Validators } from '$lib/shared/helpers/validators';
	import { inputHasValue, type IFormController } from '$lib/shared/interfaces/input-controller';
	import type { SelectInputOption } from '$lib/shared/interfaces/select-input-option';
	import { untrack } from 'svelte';
	import CheckboxGroup, {type ICheckboxOption} from '../CheckboxGroup.svelte';

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
		type,
	} = $props<{
		type: 'create' | 'edit',
		isValid: boolean;
		formValues: Record<string, string>;
		educationalInstitutions: IEducationalInstitution[];
	}>();

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
		if (!isEditMode){
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
		}

		let raIsValid: boolean = true;

		if (utfprInstitutionIsSelected()) {
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

  
  let roleOptions: ICheckboxOption[] = $state([
    { 
      id: 'admin-role', 
      label: 'Administrador do Sistema?', 
      checked: formValues.userRole === 'ADMIN' 
    }
  ]);

	$effect(() => {
    const valid = validateForm();
    
    if (isValid !== valid) {
       isValid = valid;
    }
  });

  $effect(() => {
    const isAdmin = roleOptions[0].checked;
    const newRoleValue = isAdmin ? 'ADMIN' : 'USER';

    untrack(() => {
      if (formController.userRole.value !== newRoleValue) {
        onInput('userRole', newRoleValue);
      }
    });
  });

	function onInput(formName: FormField, value: string) {
  formController[formName].value = value; 
  formController[formName].touched = true;
  
  formValues[formName] = value;
  
  isValid = validateForm();
}

	function utfprInstitutionIsSelected(): boolean {
		return formController.institutionId.value === '16610773-99dd-4df0-901f-041a3936d5d6';
	}
</script>

<div class="form-container flex w-full flex-wrap items-start gap-0 sm:gap-3 xl:gap-5">
	<FormInput
		label={'Nome Completo'}
		placeholder={'João dos Santos'}
		disabled={isEditMode}
		width="300px"
		height="90px"
		bind:value={formController.name.value}
		error={formController.name.error}
		onInput={(value) => onInput('name', value)}
	/>
	<FormInput
		label={'Telefone'}
		placeholder={'(43) 99999-9999'}
		disabled={isEditMode}
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
		disabled={isEditMode}
		type="email"
		width="300px"
		height="90px"
		bind:value={formController.email.value}
		error={formController.email.error}
		onInput={(value) => onInput('email', value)}
	/>

	{#if !isEditMode}
	<FormInput
		label={'CPF'}
		placeholder={'000.000.000-00'}
		disabled={isEditMode}
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
		disabled={isEditMode}
		width="300px"
		height="90px"
		bind:value={formController.birthDate.value}
		error={formController.birthDate.error}
		onInput={(value) => onInput('birthDate', value)}
	/>
	<SelectInput
		label={'Instituição'}
		placeholder={'Selecione sua instituição'}
		disabled={isEditMode}
		width="300px"
		height="90px"
		options={instutionSelectInputOptions}
		onChange={(value) => onInput('institutionId', value)}
		bind:value={formController.institutionId.value}
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
			bind:value={formController.ra.value}
			error={formController.ra.error}
			onInput={(value) => onInput('ra', value)}
		/>
	{/if}
  <CheckboxGroup
    bind:options={roleOptions} 
    width="300px"
    height="90px"
    justify={'center'}
    orientation="vertical"
  />
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
