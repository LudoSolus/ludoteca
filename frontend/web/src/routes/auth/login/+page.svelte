<script lang="ts">
	import { goto } from "$app/navigation";
	import { LoginUserCommand } from "$lib/api/commands/users/login-user/login-user.command";

	import Login from "$lib/components/templates/Login.svelte";
	import { CommandsHandlerService } from "$lib/shared/handlers/command/commands-handler.service";
	import { authService } from "$lib/shared/stores/auth";
	import axios from "axios";
	import { toast } from "svoast";

    const commandsHandler = new CommandsHandlerService(axios);
    let loginUserLoading: boolean = false

	function loginUser(data: Record<string, string>) {
		loginUserLoading = true;
		const command = new LoginUserCommand(
			data.email,
			data.password
		);
		commandsHandler.handle(command).subscribe({
			next: (data) => {
				const token = data.data.resultData;
				authService.login(token);
				goto("/user/home")
				loginUserLoading = false;
			},
			error: (err) => {
                toast.error(err.message, { closable: true });
				loginUserLoading = false;
			}
		});
	}
</script>

<Login {loginUserLoading} {loginUser}/>
