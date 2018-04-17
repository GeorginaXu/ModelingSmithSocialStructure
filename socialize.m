%a function that socializes graph G within t timesteps
%for each node in G, it calculates the probability matrix and choose to
%form 1 edge for each ndoe at 1 time
function [G2] = socialize(G, t)

%assign graph G to output G2
G2 = G;

%socialize t times
while t > 0
    %iterate over all nodes in G
    for u = 1:size(G2,1)
        %compute the probability of u in graph G2
        prob_matrix = computeProbability(G2, u);
        %initialize the counter of u because we only allow u to form 1 edge
        %at a time
        counter = 0;
        
        %iterate through all entries in prob_matrix of u
        for v = 1:size(prob_matrix,2)
            %check if node u already formed an egde
            if(counter ~= 1)
                uv_prob = prob_matrix(1,v);
                rand_p = rand;  %generate a random number between 0 and 1
                
                %check if the number is under the probability and there
                %hasn't been an edge between u and v
                if(rand_p <= uv_prob && G2(u,v) == 0)   
                    G2(u,v) = 1; 
                    counter = counter + 1;
                end
            end
        end
    end
    t = t-1;
end
            
            
    
