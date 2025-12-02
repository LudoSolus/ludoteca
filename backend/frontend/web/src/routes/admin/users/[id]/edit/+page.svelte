<script lang='ts'>
	import { goto } from "$app/navigation";
	import { page } from "$app/stores";
	import { EditUserCommand } from "$lib/api/commands/users/edit-user/edit-user.command";
	import type { IEditUserRequest } from "$lib/api/commands/users/edit-user/edit-user.interface";
	import type { IEducationalInstitution } from "$lib/api/queries/list-educational-institutions/list-educational-institutions.interface";
	import { ListEducationalInstitutionsQuery } from "$lib/api/queries/list-educational-institutions/list-educational-institutions.query";
	import type { IGetUserDetails } from "$lib/api/queries/users/get-user-details/get-user-details.interface";
	import { GetUserDetailsQuery } from "$lib/api/queries/users/get-user-details/get-user-details.query";
	import EditUser from "$lib/components/templates/admin/EditUser.svelte";
	import { CommandsHandlerService } from "$lib/shared/handlers/command/commands-handler.service";
  import { QueriesHandlerService } from "$lib/shared/handlers/query/queries-handler.service";
  import axios from "axios";
  import { onMount } from "svelte";
	import { toast } from "svoast";

  const queriesHandler = new QueriesHandlerService(axios);
	const commandsHandler = new CommandsHandlerService(axios);

  let userDetails: IGetUserDetails | null = $state(null);;
	let editUserLoading: boolean = $state(false);

  onMount(() => {
    fetchUser();
  });

  const fetchUser = () => {
    const userId = $page.params.id;
		if (!userId) return;

    queriesHandler.handle(new GetUserDetailsQuery(userId)).subscribe({
      next: (res) => {
        userDetails = res.resultData;
      },
      error: (err) => {
        console.error(err);
      }
      
    })
  }
  function userEdit(data: IEditUserRequest) {
    const userId = $page.params.id;
    if (!userId) return;

		editUserLoading = true;
		const command = new EditUserCommand(userId, data);
		commandsHandler.handle(command).subscribe({
			next: (data) => {
				toast.success('Usuário editado com sucesso!', { closable: true });
				goto("/admin/users")
				editUserLoading = false;
			},
			error: (err) => {
				editUserLoading = false;
			}
		});
	}
</script>

{#if userDetails}
	<EditUser user={userDetails} {userEdit} {editUserLoading} />
{:else}
	<p>Carregando...</p>
{/if}