#!/bin/bash

set -e

curl -H "Content-Type: application/json" -X GET  http://localhost:8080/chat | jq
