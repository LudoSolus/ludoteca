<script lang="ts">
	import { goto } from '$app/navigation';
	import { page } from '$app/state';
	import { ChangePasswordCommand } from '$lib/api/commands/change-password/change-password.command';
	import ChangePassword from '$lib/components/templates/ChangePassword.svelte';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import axios from 'axios';
	import { toast } from 'svoast';

	const commandsHandler = new CommandsHandlerService(axios);

	let isLoading: boolean = false;

	function handleOnChangePassword(newPassword: string) {
		const token = page.url.searchParams.get('token');
		if (!token) {
			toast.error('Você deve ter um código para mudar de senha.', { closable: true });
			goto('insert-token');
			return;
		}

		isLoading = true;

		commandsHandler.handle(new ChangePasswordCommand(token, newPassword)).subscribe({
			next: (res) => {
				toast.success('Senha alterada com sucesso, pode fazer o login.');
				isLoading = false;
				goto("/auth/login")
			},
			error: (err) => {
				isLoading = false;
			}
		});
	}
</script>

<ChangePassword onChangePassword={handleOnChangePassword} {isLoading} />
