<script lang="ts">
	import { goto } from '$app/navigation';
	import { page } from '$app/stores';
	import { DeleteUserCommand } from '$lib/api/commands/users/delete-user/delete-user.command';
	import type { IGetUserDetails } from '$lib/api/queries/users/get-user-details/get-user-details.interface';
	import { GetUserDetailsQuery } from '$lib/api/queries/users/get-user-details/get-user-details.query';
	import UserDetails from '$lib/components/templates/admin/UserDetails.svelte';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';
	import { toast } from 'svoast';

	const queriesHandler = new QueriesHandlerService(axios);
	const commandsHandler = new CommandsHandlerService(axios);

	let userDetails: IGetUserDetails | null = null;
	let isLoadingDelete: boolean = false;

	onMount(() => {
		fetchUser();
	});

	function handleDeleteUser() {
		const userId = $page.params.id;
		if (!userId) return;
		isLoadingDelete = true;

		commandsHandler.handle(new DeleteUserCommand(userId)).subscribe({
			next: (res) => {
				goto('/admin/users');
				toast.success('Usuário excluído com sucesso!');
				isLoadingDelete = false;
			},
			error: (err) => {
				isLoadingDelete = false;
			}
		});
	}

	function fetchUser() {
		const userId = $page.params.id;
		if (!userId) return;

		queriesHandler.handle(new GetUserDetailsQuery(userId)).subscribe({
			next: (res) => {
				userDetails = res.resultData;
			},
			error: (err) => {
				console.log(err);
			}
		});
	}
</script>

{#if userDetails}
	<UserDetails
		{userDetails}
		handleOnDelete={handleDeleteUser}
		{isLoadingDelete}
		handleOnEdit={() => {
			goto(`/admin/users/${$page.params.id}/edit`);
		}}
	/>
{:else}
	<p>Carregando...</p>
{/if}
