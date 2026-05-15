<script lang="ts">
	import type { ICreateUserRequest } from '$lib/api/commands/users/create-user/create-user.interface';
	import type { IEditUserRequest } from '$lib/api/commands/users/edit-user/edit-user.interface';
	import type { IEducationalInstitution } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.interface';
	import type { IGetUserDetails } from '$lib/api/queries/users/get-user-details/get-user-details.interface';
	import Button from '$lib/components/atoms/Button.svelte';
	import UserForm from '$lib/components/molecules/forms/UserForm.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	import { toast } from 'svoast';

	export let user: IGetUserDetails;
	export let editUserLoading: boolean;
	export let userEdit: (data: IEditUserRequest) => void;

	let formIsValid: boolean = false;
	let formValues: Record<keyof ICreateUserRequest, string> = {
		name: user.name,
		cpf: '',
		email: user.email,
		phone: user.phone,
		ra: user.ra,
		birthDate: new Date().toISOString().split('T')[0],
		userRole: user.userType || 'USER',
		institutionId: ''
	};
	function handleOnEdit() {
		if (!formIsValid) {
			toast.error('Preencha todos os campos devidamente.', { closable: true });
			return;
		}

		const formValuesFormated: IEditUserRequest = {
			isAdmin: formValues.userRole === 'ADMIN'
		};

		userEdit(formValuesFormated);
	}
	let educationalInstitutions: IEducationalInstitution[] = [];
</script>

<div class="px-3 py-7 sm:px-10 xl:px-15">
	<GoBack title={'Editar Usuário'} description={'Definir o tipo de usuário'} />
	<section class="flex h-full flex-col items-center justify-center gap-8 px-5 py-4 xl:px-15">
		<div>
			<UserForm
				type={'edit'}
				bind:isValid={formIsValid}
				bind:formValues
				{educationalInstitutions}
			/>
		</div>
		<Button
			text="Salvar"
			onClick={handleOnEdit}
			disabled={!formIsValid}
			loading={editUserLoading}
			width="250px"
			height="40px"
		/>
	</section>
</div>
