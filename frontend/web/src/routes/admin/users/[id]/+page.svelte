<script lang="ts">
	import { goto } from '$app/navigation';
	import { page } from '$app/stores';
	import type { IGetUserDetails } from '$lib/api/queries/users/get-user-details/get-user-details.interface';
	import { GetUserDetailsQuery } from '$lib/api/queries/users/get-user-details/get-user-details.query';
	import UserDetails from '$lib/components/templates/admin/UserDetails.svelte';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';

	const queriesHandler = new QueriesHandlerService(axios);

	let userDetails: IGetUserDetails | null = null;

	onMount(() => {
		fetchUser();
	});

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
		handleOnDelete={() => {}}
		handleOnEdit={() => {
			goto(`/admin/users/${$page.params.id}/edit`);
		}}
	/>
{:else}
	<p>Carregando...</p>
{/if}
