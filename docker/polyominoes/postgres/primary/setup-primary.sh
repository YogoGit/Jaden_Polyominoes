#!/bin/bash
echo "host replication all 0.0.0.0/0 trust" >> "$PGDATA/pg_hba.conf"
set -e
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
CREATE DATABASE $POSTGRES_DB;
EOSQL
cat >> ${PGDATA}/postgresql.conf <<EOF
wal_level = replica
archive_mode = on
archive_command = 'cd .'
max_wal_senders = 8
hot_standby = on
EOF
