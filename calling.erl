%% @author syeda
%% @doc @todo Add description to calling.


-module(calling).

%% ====================================================================
%% API functions
%% ====================================================================
-export([pReceiver/1]).
-import(exchange,[start/0]).



%% ====================================================================
%% Internal functions
%% ====================================================================


pReceiver(Pid)->
		receive
			{"rep",Sender,Timestamp}->
										whereis(master) ! {rep,Sender,Pid,Timestamp},
										pReceiver(Pid);
			{"rec", Sender}->						
						{MegaSeconds, Seconds, MicroSeconds}=erlang:now(),
						whereis(master)!{ic,Sender,Pid,MicroSeconds},
						%random:seed(),
						timer:sleep(round(timer:seconds(0.1*(rand:uniform())))),
						whereis(Sender)!{"rep",Pid,MicroSeconds},
						pReceiver(Pid)
			after 1000->io:fwrite("Process ~w has received on calls for 1 second, ending...~n",[Pid])
    end.