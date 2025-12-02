<script lang='ts'>
	import type { IUser } from "$lib/api/queries/users/list-users/list-users.interface";
	import { ListUsersQuery } from "$lib/api/queries/users/list-users/list-users.query";
	import UserManager from "$lib/components/templates/admin/UserManager.svelte";
  import { QueriesHandlerService } from "$lib/shared/handlers/query/queries-handler.service";
  import axios from "axios";
  import { onMount } from "svelte";

  const queriesHandler = new QueriesHandlerService(axios);
  let users: IUser[] = [];

  onMount(() => {
    fetchUsers();
  });

  const fetchUsers = () => {
    queriesHandler.handle(new ListUsersQuery()).subscribe({
      next: (res) => {
        users = res.resultData;
      },
      error: (err) => {
        console.error(err);
      }
      
    })
  }
</script>

<UserManager {users} />