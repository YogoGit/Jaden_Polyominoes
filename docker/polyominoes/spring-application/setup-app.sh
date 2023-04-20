#!/bin/sh
until ping -c 1 -W 1 postgres_primary
do
echo "Application waiting for primary to ping..."
sleep 3s
done
exec "$@"