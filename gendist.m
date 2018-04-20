%https://www.mathworks.com/matlabcentral/fileexchange/34101-random-numbers-from-a-discrete-distribution
function T = gendist(P,N,M,varargin)
%check for input errors
if and(nargin~=3,nargin~=4)
    error('Error:  Invalid number of input arguments.')
end

if min(P)<0
    error('Error:  All elements of first argument, P, must be positive.')
end

if or(N<1,M<1)
    error('Error:  Output matrix dimensions must be greater than or equal to one.')
end
%normalize P
Pnorm=[0 P]/sum(P);

%create cumlative distribution
Pcum=cumsum(Pnorm);

%create random matrix
N=round(N);
M=round(M);
R=rand(1,N*M);

%calculate T output matrix
V=1:length(P);
[~,inds] = histc(R,Pcum); 
T = V(inds);

%shape into output matrix
T=reshape(T,N,M);

%if desired, output plot
if nargin==4
    if strcmp(varargin{1},'plot')
        Pfreq=N*M*P/sum(P);
        figure;
        hold on
        hist(T(T>0),1:length(P))
        plot(Pfreq,'r-o')
        ylabel('Frequency')
        xlabel('P-vector Index')
        axis tight
        box on
    end
end