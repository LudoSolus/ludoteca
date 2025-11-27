<script lang="ts">
	import { page } from '$app/stores';
	import { RegisterParticipationInEventCommand } from '$lib/api/commands/event/register-participation-in-event/register-participation-in-event.query';
	import type { GetEventDetailsResponse } from '$lib/api/queries/events/get-event-details/get-event-details.interface';
	import { GetEventDetailsQuery } from '$lib/api/queries/events/get-event-details/get-event-details.query';
	import EventRegisterUserModal from '$lib/components/molecules/EventRegisterUserModal.svelte';
	import AdminEventDetails from '$lib/components/templates/admin/AdminEventDetails.svelte';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';
	import { toast } from 'svoast';

	const queriesHandler = new QueriesHandlerService(axios);
	const commandsHandler = new CommandsHandlerService(axios);

	let eventData: GetEventDetailsResponse | null = null;
	let eventRegisterUserModalIsOpen: boolean = false;
	let eventRegisterUserLoading: boolean = false;

	onMount(() => {
		fetchEvent();
	});

	function fetchEvent() {
		const eventId = $page.params.id;
		if (!eventId) return;

		queriesHandler.handle(new GetEventDetailsQuery(eventId)).subscribe({
			next: (res) => {
				eventData = res.resultData;
			},
			error: (err) => {
				console.log(err);
			}
		});
	}

	function registerParticipationInEvent(userPublicId: string) {
		eventRegisterUserLoading = true;
		const eventId = $page.params.id;
		if (!eventId) return;
		commandsHandler
			.handle(new RegisterParticipationInEventCommand(userPublicId, eventId))
			.subscribe({
				next: (res) => {
					toast.success((res as any).resultData, { closable: true });
					eventRegisterUserModalIsOpen = false;
					eventRegisterUserLoading = false;
				},
				error: (err) => {
					eventRegisterUserLoading = false;
				}
			});
	}

	function openRegisterUserModal() {
		eventRegisterUserModalIsOpen = true;
	}
</script>

{#if eventData}
	<AdminEventDetails {eventData} openRegisterUser={openRegisterUserModal} />
{:else}
	<p>Carregando...</p>
{/if}

<EventRegisterUserModal
	bind:isOpen={eventRegisterUserModalIsOpen}
	isLoading={eventRegisterUserLoading}
	onEventRegisterUser={registerParticipationInEvent}
/>
