#!/bin/bash

set -e

if [[ $# -ne 2 ]]; then
    echo "requires two arguments: nickname and message content"
    exit 1
fi

generate_message()
{
cat <<EOF
{
  "nickName": "$nickName",
  "content": "$content"
}
EOF
}

nickName=$1
content=$2

curl -H "Content-Type: application/json" -X PUT --data "$(generate_message)" http://localhost:8080/chat
