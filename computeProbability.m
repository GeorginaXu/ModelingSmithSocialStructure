%a function that takes in a graph G and a node x as input
%given the distance_matrix of x, output a probability matrix of x
%each entry of the probability matrix represents the prob for x to form an
%edge with other nodes
function[prob_matrix] = computeProbability(G,x)

%calculate the distance matrix of x
distance_matrix = computeDistance(G,x);

%initialize the prob_matrix of x
prob_matrix = zeros(1,size(distance_matrix,2));

%iterate over each entry in distance matrix
for d = 1:size(distance_matrix,2)
    %if the distance is 0 or there is already an edge between the two nodes
    if(distance_matrix(1,d) == 0 || G(x,d) == 1)
        prob_matrix(1,d) = 0;       %make its probability 0
    else
        %else, give it a certain probability based on its distance
        D = distance_matrix(1, d);
        if D == 1
            p = 0.5;
        elseif D == 2
            p = 0.3;
        else
            p = 0.1;
        end
        %update the probability matrix
        prob_matrix(1,d) = p;
    end
end