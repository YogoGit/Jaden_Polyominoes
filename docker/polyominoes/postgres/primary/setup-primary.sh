#!/bin/bash
echo "host replication all 0.0.0.0/0 trust" >> "$PGDATA/pg_hba.conf"
echo "host all postgres polyominoes.application_cluster trust" >> "$PGDATA/pg_hba.conf"
set -e
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
CREATE USER $POSTGRES_APP_USER WITH ENCRYPTED PASSWORD '$POSTGRES_APP_PASSWORD';
GRANT ALL PRIVILEGES ON DATABASE $POSTGRES_DB to $POSTGRES_APP_USER;
EOSQL
cat >> ${PGDATA}/postgresql.conf <<EOF
EOF
