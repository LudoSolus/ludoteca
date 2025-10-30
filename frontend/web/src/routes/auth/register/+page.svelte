<script lang="ts">
	import { RegisterUserCommand } from '$lib/api/commands/register-user/register-user.command';
	import { ListEducationalInstitutionsQuery } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.query';
	import Register from '$lib/components/templates/Register.svelte';
	import { UserRole } from '$lib/shared/enums/user-role.enum';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import { onlyNumbers } from '$lib/shared/helpers/only-numbers';
	import axios from 'axios';
	import { onMount } from 'svelte';

	const queriesHandler = new QueriesHandlerService(axios);
	const commandsHandler = new CommandsHandlerService(axios);

	$: registerUserLoading = false;

	onMount(() => {
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

	function registerUser(data: Record<string, string>) {
		registerUserLoading = true;
		const command = new RegisterUserCommand(
			data.name,
			onlyNumbers(data.cpf),
			data.email,
			data.password,
			onlyNumbers(data.phone),
			data.ra,
			data.birthDate as unknown as Date,
			UserRole.USER,
			data.instituteId
		);
		commandsHandler.handle(command).subscribe({
			next: (data) => {
				console.log(data);
				registerUserLoading = false;
			},
			error: (err) => {
				console.log(err);
				registerUserLoading = false;
			}
		});
	}
</script>

<Register {registerUser} {registerUserLoading} />
