FROM beakerx/beakerx:latest
MAINTAINER x@mackaber.me

USER root

ENV NB_USER tesis
ENV HOME /home/$NB_USER
RUN useradd tesis --create-home
RUN chown -R tesis:tesis /home/tesis

RUN apt-get update 
RUN apt-get install -y libtool libffi-dev ruby ruby-dev make
RUN apt-get install -y libzmq3-dev libczmq-dev
RUN gem install cztop iruby bundler
RUN iruby register --force

COPY . ${HOME}
WORKDIR $HOME
RUN bundle install
USER $NB_USER

EXPOSE 8888

CMD ["start-notebook.sh"]