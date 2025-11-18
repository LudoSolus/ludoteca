<script lang="ts">
	import { goto } from '$app/navigation';
	import { RegisterUserCommand } from '$lib/api/commands/register-user/register-user.command';
	import type { IEducationalInstitution } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.interface';
	import { ListEducationalInstitutionsQuery } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.query';
	import Register from '$lib/components/templates/Register.svelte';
	import { UserRole } from '$lib/shared/enums/user-role.enum';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import { onlyNumbers } from '$lib/shared/helpers/only-numbers';
	import { authService } from '$lib/shared/stores/auth';
	import axios from 'axios';
	import { onMount } from 'svelte';
	import { toast } from 'svoast';

	const queriesHandler = new QueriesHandlerService(axios);
	const commandsHandler = new CommandsHandlerService(axios);

	let educationalInstitutions: IEducationalInstitution[] = $state([]);
	let registerUserLoading: boolean = $state(false);

	onMount(() => {
		fetchEducationalInstituitions();
	});

	function fetchEducationalInstituitions() {
		queriesHandler.handle(new ListEducationalInstitutionsQuery()).subscribe({
			next: (data) => {
				educationalInstitutions = data.resultData;
			},
			error: (err) => {
				toast.error('Erro ao buscar instituições, recarregue a página.', { closable: true });
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
			data.ra.length > 0 ? data.ra : null,
			data.birthDate as unknown as Date,
			UserRole.USER,
			data.instituteId
		);
		commandsHandler.handle(command).subscribe({
			next: (data) => {
				toast.success('Conta criada com sucesso!', { closable: true });
				const token = data.data.resultData;
				authService.login(token);
				goto("/user/home")
				registerUserLoading = false;
			},
			error: (err) => {
				registerUserLoading = false;
			}
		});
	}
</script>

<Register {registerUser} {registerUserLoading} {educationalInstitutions} />
