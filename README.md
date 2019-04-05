# BMCC App
## TODO
- Put vault auth token in environment variable and allow pulling in into all the bootstrap.yml
- tests (spek)
- add config profiles
- service discovery
- implement spring cloud stream
- kubernetes
- kafka
- mongo
- add some actuator metrics
- grafana

## Vault setup

Provides Strava client secret authentication and needs a bit of set up.

There is a vault directory in the project and you need to create a "logs" and a "file" directory, after which you'll need to set up the vault keys at http://localhost:8200/ui. You'll also then need to copy the auth token to all bootstrap.yml files in the project.

You'll also need to add the Strava client secret key. You can either use the UI or run:

    docker exec -t vault /bin/sh -c 'export VAULT_ADDR=http://127.0.0.1:8200; export VAULT_TOKEN=<token>; vault kv put secret/strava stava.test=XXXYYYZZZ'

## Config

Config is handled by Spring Cloud Config Server.

The config module has a single class annotated with @EnableConfigServer and is configured within src/main/resources/application.yml.

It uses a prioritised composite strategy with a git repository (https://github.com/sr1977/bmcc-sotm-config.git) and vault. Any key in both will favour the one in vault. 

Each application needs a bootstrap.yml which specifies the spring.application.name - this name maps to the corresponding YAML file in the git repository or the path of the key in vault (e.g. for strava, it's strava.yml in git and secret/strava in vault). The application also needs the location of the config server (not technically needed as it will default to http://localhost:8888, but when used under docker we need the container name) and a token to authenticate with vault.

Each app requiring config can be annotated with @EnableAutoConfiguration or @RefreshScope and then can have autowired @Value properties. The refresh scope allows for hot reloading of the bean to pull in new config. This is done by POSTing to http://localhost:XXXX/actuator/refresh for each app needing updating (although check Spring Bus as think you can do all at once).

Note when you update config in git and push, then the config app will need a restart.
