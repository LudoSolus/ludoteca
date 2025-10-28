<script lang="ts">
	import { ListEducationalInstitutionsQuery } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.query';
	import Register from '$lib/components/templates/Register.svelte';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';

	const queriesHandler = new QueriesHandlerService(axios);

	onMount(() => {
		console.log('aquii');
		fetchEducationalInstituitions();
	});

	function fetchEducationalInstituitions() {
		queriesHandler.handle(new ListEducationalInstitutionsQuery()).subscribe({
			next: (data) => {
				console.log(data);
			},
			error: (err) => {
				console.log(err);
			}
		});
	}
</script>

<Register />
