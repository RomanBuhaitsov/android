FROM ubuntu:22.04

ARG DEBIAN_FRONTEND=noninteractive

LABEL maintainer="roman.buhaitsov@student.uj.edu.pl"

RUN apt-get update && apt-get upgrade -y
RUN  apt install software-properties-common -y
RUN  add-apt-repository ppa:deadsnakes/ppa -y

RUN  apt update
RUN  apt install python3.8 -y
RUN python3.8 --version
