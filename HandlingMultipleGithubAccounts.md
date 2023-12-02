
Adding multiple github accounts to same system (also works for bitbucket and gitlab)

- create ssh keys, one for each account
    ```
      ssh-keygen -t ed25519 -C "account_1@example.com"
      ssh-keygen -t ed25519 -C "account_2@example.com"
    ```
- add your ssh key to `ssh-agent` by
    ```
      ssh-add --apple-use-keychain ~/.ssh/id_rsa_[account_1]
      ssh-add --apple-use-keychain ~/.ssh/id_rsa_[account_2]
    ```
- create config file if its not in `.ssh` folder
- update the config file accordingly

  ```
    Host [account_1].github.com
      Hostname github.com
      PreferredAuthentications publickey
      IdentityFile ~/.ssh/id_rsa_[account_1]

    Host [account_2].github.com
      Hostname github.com
      PreferredAuthentications publickey
      IdentityFile ~/.ssh/id_rsa_[account_2]
  ```

- while cloning a repo from ssh, append your username in the url

  ```
    git clone git@[account_1].github.com:johndoe/advent-of-code.gi
  ```

- this modification make this command use the `id_rsa_account_1` key as it matches the host.
- this adds a record in `./git/config` file of the repo folder

  ```
    [core]
      repositoryformatversion = 0
      filemode = true
      bare = false
      logallrefupdates = true
      ignorecase = true
      precomposeunicode = true
    [remote "origin"]
      url = git@[account_1].github.com:johndoe/advent-of-code.git
      fetch = +refs/heads/*:refs/remotes/origin/*
  ```

- now whenever we do a git operation, it always refers to `git@[account_1].github.com:johndoe/advent-of-code.git`
  as the repo and hence it always picks `id_rsa_account_1` as the user
- do remember to configure username and email for this repo, since the config are localized you have to do this whenever
  you add repo

    ```
      git config --local user.name "johndoe"
      git config --local user.email "johndoe@gmail.com"
    ```

- your config should look something like this

    ```
      [core]
        repositoryformatversion = 0
        filemode = true
        bare = false
        logallrefupdates = true
        ignorecase = true
        precomposeunicode = true
      [remote "origin"]
        url = git@pet.github.com:johndoe/advent-of-code.git
        fetch = +refs/heads/*:refs/remotes/origin/*
      [branch "main"]
        remote = origin
        merge = refs/heads/main
      [user]
        email = johndoe@gmail.com
        name = johndoe
    ```
- you can verify your configs by accessing `./git/config` in your repo or with

  ```
    git config --local --list
  ```