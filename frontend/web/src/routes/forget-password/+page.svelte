<script lang="ts">
	import { goto } from '$app/navigation';
	import { RequestPasswordResetCommand } from '$lib/api/commands/request-password-reset/request-password-reset.command';
	import ForgetPassword from '$lib/components/templates/ForgetPassword.svelte';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import axios from 'axios';
	import { toast } from 'svoast';

	const commandsHandler = new CommandsHandlerService(axios);
	let isLoading: boolean = false;

	function requestPasswordReset(email: string) {
		isLoading = true;

		commandsHandler.handle(new RequestPasswordResetCommand(email)).subscribe({
			next: (res) => {
				toast.success('Um código foi enviado ao seu e-mail.', { closable: true });
				goto('/forget-password/insert-token');
				isLoading = false;
			},
			error: (err) => {
				isLoading = false;
			}
		});
	}
</script>

<ForgetPassword onSendEmail={requestPasswordReset} {isLoading} />
