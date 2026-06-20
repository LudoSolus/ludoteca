<script lang="ts">
	import type { ICreateUserRequest } from '$lib/api/commands/users/create-user/create-user.interface';
	import type { IEditUserRequest } from '$lib/api/commands/users/edit-user/edit-user.interface';
	import type { IEducationalInstitution } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.interface';
	import type { IGetUserDetails } from '$lib/api/queries/users/get-user-details/get-user-details.interface';
	import Button from '$lib/components/atoms/Button.svelte';
	import UserForm from '$lib/components/molecules/forms/UserForm.svelte';
	import GoBack from '$lib/components/molecules/GoBack.svelte';
	import { toast } from 'svoast';

	let {
		user = null,
		editUserLoading,
		userEdit
	} = $props<{
		user: IGetUserDetails | null;
		editUserLoading: boolean;
		userEdit: (data: IEditUserRequest) => void;
	}>();

	let formIsValid = $state(false);
	let formValues = $state<Record<keyof ICreateUserRequest, string>>({
		name: '',
		cpf: '',
		email: '',
		phone: '',
		ra: '',
		birthDate: new Date().toISOString().split('T')[0],
		userRole: 'USER',
		institutionId: ''
	});

	$effect(() => {
		if (user) {
			formValues = {
				name: user.name,
				cpf: '',
				email: user.email,
				phone: user.phone,
				ra: user.ra,
				birthDate: new Date().toISOString().split('T')[0],
				userRole: user.userType || 'USER',
				institutionId: ''
			};
		}
	});

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

<main class="px-3 py-7 sm:px-10 xl:px-15">
	<GoBack title={'Editar Usuário'} description={'Definir o tipo de usuário'} />
	{#if user === null}
		<section class="flex h-full flex-col items-center justify-center gap-8 px-5 py-4 xl:px-15">
			<div class="form-container flex w-full flex-wrap items-start gap-0 sm:gap-3 xl:gap-5">
				{#each Array(3) as _}
					<div class="flex flex-col gap-2" style="width: 300px; height: 90px;">
						<div class="skeleton-pulse h-4 w-28 rounded bg-gray-300"></div>
						<div class="skeleton-pulse h-10 w-full rounded border border-gray-200 bg-gray-100"></div>
					</div>
				{/each}
				<div class="flex w-full items-center gap-2 mt-2">
					<div class="skeleton-pulse h-5 w-5 rounded border border-gray-200 bg-gray-100"></div>
					<div class="skeleton-pulse h-4 w-48 rounded bg-gray-300"></div>
				</div>
			</div>
			<div class="skeleton-pulse h-10 w-[250px] rounded bg-gray-300"></div>
		</section>
	{:else}
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
	{/if}
</main>

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
