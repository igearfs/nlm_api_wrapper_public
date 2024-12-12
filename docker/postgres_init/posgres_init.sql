-- Copyright (C) 2024 In-Game Event, A Red Flag Syndicate LLC
--
-- This program is free software: you can redistribute it and/or modify it under the terms of the Server Side Public License, version 1, as published by MongoDB, Inc., with the following additional terms:
--
-- - Any use of this software in a commercial capacity requires a commercial license agreement with In-Game Event, A Red Flag Syndicate LLC. Contact licence_request@igearfs.com for details.
--
-- - If you choose not to obtain a commercial license, you must comply with the SSPL terms, which include making publicly available the source code for all programs, tooling, and infrastructure used to operate this software as a service.
--
-- This program is distributed WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Server Side Public License for more details.
--
-- For licensing inquiries, contact: licence_request@igearfs.com


-- Create the databases 
CREATE DATABASE nlm_databasee;

-- Create users for Mirth Connect
CREATE USER nlm_admin WITH PASSWORD 'nlm_admin';
-- Grant privileges to the users on the databases
GRANT ALL PRIVILEGES ON DATABASE nlm_databasee TO nlm_admin;


-- Switch to nlm_databasee and create the schema and permissions
\c nlm_databasee;
CREATE SCHEMA IF NOT EXISTS nlm_databasee;

-- Grant permissions to nlm_admin for nlm_databasee
GRANT USAGE, CREATE ON SCHEMA nlm_databasee TO nlm_admin;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA nlm_databasee TO nlm_admin;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA nlm_databasee TO nlm_admin;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA nlm_databasee TO nlm_admin;

-- Set default privileges for nlm_admin (custom schema)
ALTER DEFAULT PRIVILEGES IN SCHEMA nlm_databasee GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO nlm_admin;
ALTER DEFAULT PRIVILEGES IN SCHEMA nlm_databasee GRANT EXECUTE ON FUNCTIONS TO nlm_admin;
ALTER DEFAULT PRIVILEGES IN SCHEMA nlm_databasee GRANT USAGE, SELECT ON SEQUENCES TO nlm_admin;

