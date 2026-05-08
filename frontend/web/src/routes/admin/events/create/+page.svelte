<script lang="ts">
	import { goto } from '$app/navigation';
	import { RegisterEventCommand } from '$lib/api/commands/events/register-event/register-event.command';
	import type { IRegisterEventRequest } from '$lib/api/commands/events/register-event/register-event.interface';
	import EventRegister from '$lib/components/templates/admin/EventRegister.svelte';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import axios from 'axios';
	import { toast } from 'svoast';

	const commandsHandler = new CommandsHandlerService(axios);
	let isLoading: boolean = false;

	function registerevent(data: IRegisterEventRequest) {
		isLoading = true;
		const command = new RegisterEventCommand(data);
		commandsHandler.handle(command).subscribe({
			next: (data) => {
				goto('/admin/events');
				isLoading = false;
			},
			error: (err) => {
				toast.error(err.message, { closable: true });
				isLoading = false;
			}
		});
	}
</script>

<EventRegister {isLoading} onCreateEvent={registerevent} />
