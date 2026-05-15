<script lang="ts">
	import { goto } from '$app/navigation';
	import { CreateUserCommand } from '$lib/api/commands/users/create-user/create-user.command';
	import type { ICreateUserRequest } from '$lib/api/commands/users/create-user/create-user.interface';
	import type { IEducationalInstitution } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.interface';
	import { ListEducationalInstitutionsQuery } from '$lib/api/queries/list-educational-institutions/list-educational-institutions.query';
	import UserRegister from '$lib/components/templates/admin/UserRegister.svelte';
	import { CommandsHandlerService } from '$lib/shared/handlers/command/commands-handler.service';
	import { QueriesHandlerService } from '$lib/shared/handlers/query/queries-handler.service';
	import axios from 'axios';
	import { onMount } from 'svelte';
	import { toast } from 'svoast';

	const queriesHandler = new QueriesHandlerService(axios);
	const commandsHandler = new CommandsHandlerService(axios);

	let educationalInstitutions: IEducationalInstitution[] = $state([]);
	let createUserLoading: boolean = $state(false);

	onMount(() => {
		fetchEducationalInstitutions();
	});

	function fetchEducationalInstitutions() {
		queriesHandler.handle(new ListEducationalInstitutionsQuery()).subscribe({
			next: (data) => {
				educationalInstitutions = data.resultData;
			},
			error: (err) => {
				toast.error('Erro ao buscar instituições, recarregue a página.', { closable: true });
			}
		});
	}

	function createUser(data: ICreateUserRequest) {
		createUserLoading = true;
		const command = new CreateUserCommand(data);
		commandsHandler.handle(command).subscribe({
			next: (data) => {
				toast.success('Usuário criado com sucesso!', { closable: true });
				goto('/admin/users');
				createUserLoading = false;
			},
			error: (err) => {
				createUserLoading = false;
			}
		});
	}
</script>

<UserRegister {createUser} {createUserLoading} {educationalInstitutions} />
