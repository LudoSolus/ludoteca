<script lang="ts">
	import { goto } from '$app/navigation';
	import type { IUser } from '$lib/api/queries/users/list-users/list-users.interface';
	import Fa from 'svelte-fa';
	import Button from '$lib/components/atoms/Button.svelte';
	import SearchInput from '$lib/components/atoms/SearchInput.svelte';
	import UserContainer from '$lib/components/atoms/UserContainer.svelte';
	import UserContainerSkeleton from '$lib/components/atoms/UserContainerSkeleton.svelte';
	import { faPlus, faUserSlash } from '@fortawesome/free-solid-svg-icons';

	export let users: IUser[] | undefined = undefined;
	let searchInputLeftValue: string = '';
	let searchInputRightValue: string = '';

	const goToUserDetails = (userId: string) => {
		goto(`/admin/users/${userId}`);
	};
	const goToUserCreate = () => {
		goto(`/admin/users/create`);
	};

	$: isLoading = users === undefined;

	$: filteredUsersList = users
		? users.filter((user) => {
				return (
					user.email.toLowerCase().includes(searchInputLeftValue.toLowerCase()) &&
					user.roleUser !== 'ADMIN'
				);
			})
		: [];
	$: filteredAdminsList = users
		? users.filter((user) => {
				return (
					user.email.toLowerCase().includes(searchInputRightValue.toLowerCase()) &&
					user.roleUser === 'ADMIN'
				);
			})
		: [];
</script>

<main class="flex w-full flex-col px-2 pt-7 pb-20 sm:px-4 md:px-10">
	<div class="flex w-full justify-end">
		<Button
			text={'Criar'}
			leftIcon={faPlus}
			onClick={() => {
				goToUserCreate();
			}}
		/>
	</div>
	<div
		class="section-height mt-6 flex w-full max-w-350 flex-col items-start justify-center gap-10 lg:flex-row lg:justify-between lg:gap-20 sm:mt-0"
	>
		<!-- USERS -->
		<section
			class="flex w-full max-w-150 flex-1 flex-col items-center justify-start gap-8"
		>
			<h3 class="h3 w-full text-start">Usuários</h3>
			<div class="box-list flex flex-col gap-8 px-1">
				<div class="flex w-full items-center justify-start">
					<SearchInput
						bind:value={searchInputLeftValue}
						placeholder="Pesquisar..."
					/>
				</div>

				<div
					class="user-list scrollbar-clean flex w-full flex-col items-center gap-2 overflow-y-auto px-1 py-2 md:px-4"
				>
					{#if isLoading}
						{#each Array(4) as _}
							<UserContainerSkeleton />
						{/each}
					{:else if filteredUsersList.length > 0}
						{#each filteredUsersList as user}
							<UserContainer
								email={user.email}
								ageGroup={user.ageCategory === '+18' ? 'Maioridade' : user.ageCategory}
								onClick={() => {
									goToUserDetails(user.id);
								}}
							/>
						{/each}
					{:else}
						<div
							class="flex w-full flex-col items-center justify-center gap-3 rounded-2xl border-2 border-dashed border-black p-5 text-center shadow-[0px_4px_10px_rgba(0,0,0,0.25)]"
						>
							<div
								class="flex h-12 w-12 items-center justify-center rounded-full border-2 border-black bg-(--primary-color) text-black"
							>
								<Fa icon={faUserSlash} size="lg" />
							</div>
							<div class="flex flex-col gap-1">
								<h4 class="inknut text-sm font-bold">Nenhum usuário</h4>
								<p class="text-xs text-gray-700">Nenhum usuário foi encontrado.</p>
							</div>
						</div>
					{/if}
				</div>
			</div>
		</section>

		<!-- ADMINS -->
		<section
			class="flex w-full max-w-150 flex-1 flex-col items-center justify-start gap-8"
		>
			<h3 class="h3 w-full text-start">Administradores</h3>
			<div class="box-list flex flex-col gap-8 px-1">
				<div class="flex w-full items-center justify-start">
					<SearchInput
						bind:value={searchInputRightValue}
						placeholder="Pesquisar..."
					/>
				</div>

				<div
					class="user-list scrollbar-clean flex w-full flex-col items-center gap-2 overflow-y-auto px-1 py-2 md:px-4"
				>
					{#if isLoading}
						{#each Array(4) as _}
							<UserContainerSkeleton />
						{/each}
					{:else if filteredAdminsList.length > 0}
						{#each filteredAdminsList as admin}
							<UserContainer
								email={admin.email}
								ageGroup={admin.ageCategory === '+18' ? 'Maioridade' : admin.ageCategory}
								isRpgMaster={true}
								onClick={() => {
									goToUserDetails(admin.id);
								}}
							/>
						{/each}
					{:else}
						<div
							class="flex w-full flex-col items-center justify-center gap-3 rounded-2xl border-2 border-dashed border-black p-5 text-center shadow-[0px_4px_10px_rgba(0,0,0,0.25)]"
						>
							<div
								class="flex h-12 w-12 items-center justify-center rounded-full border-2 border-black bg-(--primary-color) text-black"
							>
								<Fa icon={faUserSlash} size="lg" />
							</div>
							<div class="flex flex-col gap-1">
								<h4 class="inknut text-sm font-bold">Nenhum administrador</h4>
								<p class="text-xs text-gray-700">Nenhum administrador foi encontrado.</p>
							</div>
						</div>
					{/if}
				</div>
			</div>
		</section>
	</div>
</main>

<style>
	.section-height {
		height: calc(100% - 54px);
		min-height: fit-content;
	}

	.box-list {
		height: calc(100% - 77px);
		width: 100%;
	}

	.user-list {
		height: fit-content;
		max-height: 400px;
		scrollbar-width: thin;
	}
</style>
