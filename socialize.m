%a function that socializes graph G within t timesteps
%for each node in G, it calculates the probability matrix and choose to
%form 1 edge for each ndoe at 1 time
function [G2] = socialize(G,S,J,P,F,t,method)

if strcmp(method, "distance")
    %assign graph G to output G2
    G2 = G;
    
    %socialize t times
    while t > 0
        %iterate over all nodes in G
        for u = 1:length(G)
            %compute the probability of u in graph G2
            prob_matrix = computeProbability(G, u, "distance");
            if max(prob_matrix)>0
                v = gendist(prob_matrix,1,1);
                G2(u,v)=1;
                G2(v,u)=1;
            end
        end
        G=G2;
        t = t-1;
    end
    G2=G;

elseif strcmp(method, "richer")
    G2 = G;
    
    %socialize t times
    while t > 0
        %iterate over all nodes in G
        for u = 1:length(G)
            %compute the probability of u in graph G2
            prob_matrix = computeProbability(G, u, "richer");
            if max(prob_matrix)>0
                v = gendist(prob_matrix,1,1);
                G2(u,v)=1;
                G2(v,u)=1;
            end
        end
        G=G2;
        t = t-1;
    end
    G2=G; 
    
elseif strcmp(method, "classyear")
    G2 = G;
    
    %socialize t times
    while t > 0
        %iterate over all nodes in G
        for u = 1:length(G)
            %compute the probability of u in graph G2
            prob_matrix = computeProbability(G, u, "classyear",S,J,P,F);
            if max(prob_matrix)>0
                v = gendist(prob_matrix,1,1);
                G2(u,v)=1;
                G2(v,u)=1;
            end
        end
        G=G2;
        t = t-1;
    end
    G2=G; 
end
    
