<script lang="ts">
	import type { ICreateUserRequest } from '$lib/api/commands/users/create-user/create-user.interface';
	import type { IEducationalInstitution } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.interface';
	import Button from '$lib/components/atoms/Button.svelte';
	import UserForm from '$lib/components/molecules/forms/UserForm.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	import type { EUserRole } from '$lib/shared/enums/user-role.enum';
	import { onlyNumbers } from '$lib/shared/helpers/only-numbers';
	import { toast } from 'svoast';

	export let educationalInstitutions: IEducationalInstitution[];
	export let createUserLoading: boolean;
	export let createUser: (data: ICreateUserRequest) => void;

	let formIsValid: boolean = false;
	let formValues: Record<keyof ICreateUserRequest, string > = {
		name: '',
		cpf: '',
		email: '',
		phone: '',
		ra: '',
		birthDate: new Date().toISOString().split('T')[0],
    userRole: 'USER',
		institutionId: '',
	};

	function handleOnCreateUser() {
		if (!formIsValid) {
			toast.error('Preencha todos os campos.', { closable: true });
			return;
		}
		const formValuesFormated: ICreateUserRequest = {
			...formValues,
			phone: onlyNumbers(formValues.phone),
			cpf: onlyNumbers(formValues.cpf),
			birthDate: formValues.birthDate as unknown as Date,
			userRole: formValues.userRole as unknown as EUserRole,
			ra: formValues.ra === '' ? null : formValues.ra,
		}
		createUser(formValuesFormated);
	}
</script>
<div class="px-3 py-7 sm:px-10 xl:px-15">
	<GoBack title={'Registrar Usuário'} description={'Criar um usuário ou administrador'} />
	<section class="flex h-full flex-col items-center justify-center gap-8 px-5 py-4 xl:px-15">
	
		<div>
			<UserForm type={'create'} bind:isValid={formIsValid} bind:formValues {educationalInstitutions} />
		</div>
		<Button
			text="Registrar Usuário"
			onClick={handleOnCreateUser}
			disabled={!formIsValid}
			loading={createUserLoading}
			width="250px"
			height="40px"
		/>
	</section>
</div>
