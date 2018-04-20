%a function that takes in a graph G and a node x as input
%given the distance_matrix of x, output a probability matrix of x
%each entry of the probability matrix represents the prob for x to form an
%edge with other nodes
function[prob_matrix] = computeProbability(G,x,method,S,J,P,F)

if strcmp(method,"distance")
    %calculate the distance matrix of x
    distance_matrix = computeDistance(G,x);
    
    %initialize the prob_matrix of x
    prob_matrix = zeros(1,size(distance_matrix,2));
    
    %iterate over each entry in distance matrix
    for d = 1:size(distance_matrix,2)
        D = distance_matrix(1, d);%the distance between x and d
        %if the distance is 0 or there is already an edge between the two nodes
        if(D == 0 || G(x,d) == 1)
            prob_matrix(1,d) = 0;       %make its probability 0
        else
            %else, give it a certain probability based on D, (D>=2)
            if D == 2
                p = 0.4;
            elseif D == 3
                p = 0.2;
            else
                p = 0.1;
            end
            %update the probability matrix
            prob_matrix(1,d) = p;
        end
    end
    
elseif strcmp(method,"richer")
    prob_matrix = sum(G);
    prob_matrix(1,x) = 0;
    for u = 1:length(G)
        if(G(x,u) == 1)
            prob_matrix(1,u) = 0;
        end
    end 
    
elseif strcmp(method, "classyear")
    %initialize the prob_matrix of x
    prob_matrix = zeros(1,size(length(G),1));
    classOfX = computeClass(x,S,J,P,F);
    for u = 1:length(G)
        if (u == x || G(u,x) == 1)
            prob_matrix(1,u) = 0;
        elseif(classOfX == computeClass(u,S,J,P,F))
            prob_matrix(1,u) = 0.4;
        elseif(abs(classOfX - computeClass(u,S,J,P,F)) == 1)
            prob_matrix(1,u) = 0.3;
        elseif(abs(classOfX - computeClass(u,S,J,P,F)) == 2)
            prob_matrix(1,u) = 0.2;
        elseif(abs(classOfX - computeClass(u,S,J,P,F)) == 3)
            prob_matrix(1,u) = 0.1;
        end
    end
end 