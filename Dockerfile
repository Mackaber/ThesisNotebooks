FROM beakerx/beakerx:1.0.0
MAINTAINER x@mackaber.me

USER root

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