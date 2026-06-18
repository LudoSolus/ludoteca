<script lang="ts">
	import type { IUpdateUserRequest } from '$lib/api/commands/users/update-user/update-user.interface';
	import type { IEducationalInstitution } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.interface';
	import type { IGetUserProfileDetailsResponse } from '$lib/api/queries/users/get-user-profile-details/get-user-profile-details.interface';
	import { toast } from 'svoast';
	import Button from '../atoms/Button.svelte';
	import EditProfileForm from '../molecules/forms/EditProfileForm.svelte';
	import GoBack from '../molecules/GoBack.svelte';
	import { stringIsValid } from '$lib/shared/helpers/string-is-valid';

	let { isLoading, educationalInstitutions, userData, onEdit } = $props<{
		isLoading: boolean;
		educationalInstitutions: IEducationalInstitution[];
		userData: IGetUserProfileDetailsResponse | null;
		onEdit: (userData: IUpdateUserRequest) => void;
	}>();

	let formIsValid: boolean = $state(false);
	let formValues: Record<string, string> = $derived(
		userData
			? {
					publicId: userData.publicId,
					name: userData.name,
					phone: userData.phone,
					email: userData.email,
					cpf: userData.cpf,
					birthDate: userData.birthDate ? userData.birthDate.toISOString().split('T')[0] : '',
					institutionId: userData.institutionId,
					ra: userData.ra
				}
			: {
					publicId: '',
					name: '',
					phone: '',
					email: '',
					cpf: '',
					birthDate: '',
					institutionId: '',
					ra: ''
				}
	);

	function handleOnEdit() {
		if (!formIsValid) {
			toast.error('Preencha todas as informações.');
			return;
		}

		const selectedInstitution: IEducationalInstitution | undefined = educationalInstitutions.find(
			(ei: IEducationalInstitution) => ei.institutionId == formValues.institutionId
		);

		const body: IUpdateUserRequest = {
			name: formValues.name,
			email: formValues.email,
			phone: formValues.phone,
			institutionId: selectedInstitution ? selectedInstitution.institutionId : undefined,
			ra: selectedInstitution
				? selectedInstitution.isUtfpr
					? formValues.ra
					: undefined
				: undefined
		};

		onEdit(body);
	}
</script>

<main class="flex w-full flex-col items-center gap-5 px-3 py-7 sm:px-10 md:gap-10 xl:px-15">
	<GoBack title="Perfil" description="Seus dados" />
	{#if userData === null}
		<div
			class="grid w-full max-w-230 grid-cols-1 place-items-center gap-0 sm:grid-cols-2 sm:gap-3 xl:gap-5"
		>
			{#each Array(7) as _}
				<div class="flex w-full flex-col gap-2" style="height: 90px;">
					<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
					<div class="skeleton-pulse h-10 w-full rounded border border-gray-200 bg-gray-100"></div>
				</div>
			{/each}
		</div>
		<div class="skeleton-pulse mt-5 h-10 w-[290px] rounded bg-gray-300"></div>
	{:else}
		<EditProfileForm bind:isValid={formIsValid} bind:formValues {educationalInstitutions} />
		<Button text="Salvar" width="290px" height="40px" onClick={handleOnEdit} loading={isLoading} />
	{/if}
</main>

<style>
	.skeleton-pulse {
		animation: pulse 1.5s cubic-bezier(0.4, 0, 0.6, 1) infinite;
	}

	@keyframes pulse {
		0%, 100% {
			opacity: 1;
		}
		50% {
			opacity: 0.4;
		}
	}
</style>
