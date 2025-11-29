<script lang="ts">
	import type { IUpdateUserRequest } from '$lib/api/commands/users/update-user/update-user.interface';
	import type { IEducationalInstitution } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.interface';
	import type { IGetUserProfileDetailsResponse } from '$lib/api/queries/users/get-user-profile-details/get-user-profile-details.interface';
	import Button from '../atoms/Button.svelte';
	import EditProfileForm from '../molecules/forms/EditProfileForm.svelte';
	import GoBack from '../molecules/GoBack.svelte';

	let { isLoading, educationalInstitutions, userData, onEdit } = $props<{
		isLoading: boolean;
		educationalInstitutions: IEducationalInstitution[];
		userData: IGetUserProfileDetailsResponse;
		onEdit: (userData: IUpdateUserRequest) => void;
	}>();

	let formIsValid: boolean = $state(false);
	let formValues: Record<string, string> = $derived({
		publicId: userData.publicId,
		name: userData.name,
		phone: userData.phone,
		email: userData.email,
		cpf: userData.cpf,
		birthDate: userData.birthDate.toISOString().split('T')[0],
		instituitionId: userData.instituitionId,
		ra: userData.ra
	});

	function handleOnEdit() {}
</script>

<main class="flex w-full flex-col items-center gap-5 px-3 py-7 sm:px-10 md:gap-10 xl:px-15">
	<GoBack title="Perfil" description="Seus dados" />
	<EditProfileForm bind:isValid={formIsValid} bind:formValues {educationalInstitutions} />
	<Button text="Salvar" width="290px" height="40px" onClick={handleOnEdit} loading={isLoading} />
</main>
